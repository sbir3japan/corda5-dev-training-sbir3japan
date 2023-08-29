package com.r3.developers.apples.workflows;

import com.r3.developers.apples.contracts.stamp.AppleStampContract;
import com.r3.developers.apples.contracts.BasketOfApplesContract;
import com.r3.developers.apples.states.AppleStamp;
import com.r3.developers.apples.states.BasketOfApples;
import net.corda.v5.application.flows.ClientRequestBody;
import net.corda.v5.application.flows.ClientStartableFlow;
import net.corda.v5.application.flows.CordaInject;
import net.corda.v5.application.flows.InitiatingFlow;
import net.corda.v5.application.marshalling.JsonMarshallingService;
import net.corda.v5.application.membership.MemberLookup;
import net.corda.v5.application.messaging.FlowMessaging;
import net.corda.v5.application.messaging.FlowSession;
import net.corda.v5.base.annotations.Suspendable;
import net.corda.v5.base.types.MemberX500Name;
import net.corda.v5.ledger.common.NotaryLookup;
import net.corda.v5.ledger.utxo.StateAndRef;
import net.corda.v5.ledger.utxo.UtxoLedgerService;
import net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction;
import net.corda.v5.membership.NotaryInfo;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


/**
 * RedeemApplesFlow:
 * リンゴの引換券と箱詰めされたリンゴを交換するFlow。
 * 購入者は引換券を消費して、リンゴの所有者となる。
 */
@InitiatingFlow(protocol = "redeem-apples")
public class RedeemApplesFlow implements ClientStartableFlow {

    @CordaInject
    private FlowMessaging flowMessaging;

    @CordaInject
    private JsonMarshallingService jsonMarshallingService;

    @CordaInject
    private MemberLookup memberLookup;

    @CordaInject
    private NotaryLookup notaryLookup;

    @CordaInject
    private UtxoLedgerService utxoLedgerService;
    @NotNull
    @Suspendable
    @Override
    public String call(ClientRequestBody requestBody) {

        RedeemApplesRequest request = requestBody.getRequestBodyAs(jsonMarshallingService, RedeemApplesRequest.class);
        MemberX500Name buyerName = request.getBuyer();
        UUID stampId = request.getStampId();

        // Retrieve the notaries public key (this will change)
        NotaryInfo notaryInfo = notaryLookup.getNotaryServices().iterator().next();

        PublicKey myKey = memberLookup.myInfo().getLedgerKeys().get(0);

        PublicKey buyer;
        try {
            buyer = Objects.requireNonNull(memberLookup.lookup(buyerName)).getLedgerKeys().get(0);
        } catch (Exception e) {
            throw new IllegalArgumentException("The buyer does not exist within the network");
        }

        StateAndRef<AppleStamp> appleStampStateAndRef;
        try {
            appleStampStateAndRef = utxoLedgerService
                    .findUnconsumedStatesByType(AppleStamp.class)
                    .stream()
                    .filter(stateAndRef -> stateAndRef.getState().getContractState().getId().equals(stampId))
                    .iterator()
                    .next();
        } catch (Exception e) {
            throw new IllegalArgumentException("There are no eligible basket of apples");
        }

        StateAndRef<BasketOfApples> basketOfApplesStateAndRef;
        try {
            basketOfApplesStateAndRef = utxoLedgerService
                    .findUnconsumedStatesByType(BasketOfApples.class)
                    .stream()
                    .filter(
                            stateAndRef -> stateAndRef.getState().getContractState().getOwner().equals(
                                    appleStampStateAndRef.getState().getContractState().getIssuer()
                            )
                    )
                    .iterator()
                    .next();
        } catch (Exception e) {
            throw new IllegalArgumentException("There are no eligible baskets of apples");
        }

        BasketOfApples originalBasketOfApples = basketOfApplesStateAndRef.getState().getContractState();

        BasketOfApples updatedBasket = originalBasketOfApples.changeOwner(buyer);

        //Create the transaction
        UtxoSignedTransaction transaction = utxoLedgerService.createTransactionBuilder()
                .setNotary(notaryInfo.getName())
                .addInputStates(appleStampStateAndRef.getRef(), basketOfApplesStateAndRef.getRef())
                .addOutputState(updatedBasket)
                .addCommand(new AppleStampContract.AppleCommands.Redeem())
                .addCommand(new BasketOfApplesContract.BasketOfApplesCommands.Move())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(myKey, buyer))
                .toSignedTransaction();

        FlowSession session = flowMessaging.initiateFlow(buyerName);

        try {
            // Send the transaction and state to the counterparty and let them sign it
            // Then notarise and record the transaction in both parties' vaults.
            return utxoLedgerService.finalize(transaction, List.of(session)).toString();
        } catch (Exception e) {
            return String.format("Flow failed, message: %s", e.getMessage());
        }
    }
}