package com.r3.developers.apples.workflows;

import com.r3.developers.apples.contracts.applestamp.AppleStampContract;
import com.r3.developers.apples.contracts.basketofapples.BasketOfApplesContract;
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
import net.corda.v5.membership.MemberInfo;
import net.corda.v5.membership.NotaryInfo;
import org.jetbrains.annotations.NotNull;
import java.security.PublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * AppleStamp と BasketOfApples のDvP決済をするFlow
 */
@InitiatingFlow(protocol = "redeem-apples")
public class RedeemApplesFlow implements ClientStartableFlow {

    @CordaInject
    FlowMessaging flowMessaging;

    @CordaInject
    JsonMarshallingService jsonMarshallingService;

    @CordaInject
    MemberLookup memberLookup;

    @CordaInject
    NotaryLookup notaryLookup;

    @CordaInject
    UtxoLedgerService utxoLedgerService;

    public RedeemApplesFlow() {}

    @Suspendable
    @Override
    @NotNull
    public String call(@NotNull ClientRequestBody requestBody) {

        RedeemApplesRequest request = requestBody.getRequestBodyAs(jsonMarshallingService, RedeemApplesRequest.class);
        MemberX500Name buyerName = request.getBuyer();
        UUID stampId = request.getStampId();

        NotaryInfo notaryInfo = notaryLookup.getNotaryServices().iterator().next();

        PublicKey myKey = memberLookup.myInfo().getLedgerKeys().get(0);

        PublicKey buyer;
        try {
            buyer = Objects.requireNonNull(memberLookup.lookup(buyerName)).getLedgerKeys().get(0);
        } catch (Exception e) {
            throw new IllegalArgumentException("The buyer does not exist within the network");
        }

        // InputState に含めるべき AppleStampState を構築しています.
        StateAndRef<AppleStamp> appleStampStateAndRef;
        try {
            appleStampStateAndRef = utxoLedgerService
                    .findUnconsumedStatesByExactType(AppleStamp.class, 100, Instant.now()).getResults()
                    .stream()
                    .filter(stateAndRef -> stateAndRef.getState().getContractState().getId().equals(stampId))
                    .iterator()
                    .next();
        } catch (Exception e) {
            throw new IllegalArgumentException("There are no eligible basket of apples");
        }

        // 実装1: InputState に含めるべき BasketOfApplesState を構築してください.
        StateAndRef<BasketOfApples> basketOfApplesStampStateAndRef;
        try {
            basketOfApplesStampStateAndRef = utxoLedgerService
                    .findUnconsumedStatesByExactType(BasketOfApples.class, 100, Instant.now()).getResults()
                    .stream()
                    .iterator()
                    .next();
        } catch (Exception e) {
            throw new IllegalArgumentException("There are no eligible baskets of apples");
        }

        // 実装2: InputState に含めるべき BasketOfApplesState から、OutputState になる BasketOfApples(ContractState) を作成してください.
        BasketOfApples originalBasketOfApples;
        BasketOfApples updatedBasket;

        // 実装3: Transaction に InputState と OutputState を設定してください.
        UtxoSignedTransaction transaction = utxoLedgerService.createTransactionBuilder()
                .setNotary(notaryInfo.getName())
                .addCommand(new AppleStampContract.AppleCommands.Redeem())
                .addCommand(new BasketOfApplesContract.Move())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(myKey, buyer))
                .toSignedTransaction();

        FlowSession session = flowMessaging.initiateFlow(buyerName);

        try {
            String transactionId = utxoLedgerService.finalize(transaction, List.of(session)).getTransaction().getId().toString();
            return jsonMarshallingService.format(new RedeemApplesResponse(transactionId, memberLookup.myInfo().getName().getCommonName(), buyerName.getCommonName()));
        } catch (Exception e) {
            return String.format("Flow failed, message: %s", e.getMessage());
        }
    }
}
