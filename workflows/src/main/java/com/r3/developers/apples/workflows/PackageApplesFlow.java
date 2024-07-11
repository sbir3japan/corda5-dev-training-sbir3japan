package com.r3.developers.apples.workflows;

import com.r3.developers.apples.contracts.applestamp.AppleStampContract;
import com.r3.developers.apples.contracts.basketofapples.BasketOfApplesContract;
import com.r3.developers.apples.states.BasketOfApples;
import net.corda.v5.application.flows.ClientRequestBody;
import net.corda.v5.application.flows.ClientStartableFlow;
import net.corda.v5.application.flows.CordaInject;
import net.corda.v5.application.marshalling.JsonMarshallingService;
import net.corda.v5.application.membership.MemberLookup;
import net.corda.v5.base.annotations.Suspendable;
import net.corda.v5.ledger.common.NotaryLookup;
import net.corda.v5.ledger.utxo.UtxoLedgerService;
import net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction;
import net.corda.v5.membership.NotaryInfo;
import org.jetbrains.annotations.NotNull;
import java.security.PublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

/**
 * farmer がりんごをバスケットに詰めるFlow
 */
public class PackageApplesFlow implements ClientStartableFlow {

    @CordaInject
    JsonMarshallingService jsonMarshallingService;

    @CordaInject
    MemberLookup memberLookup;

    @CordaInject
    NotaryLookup notaryLookup;

    @CordaInject
    UtxoLedgerService utxoLedgerService;

    public PackageApplesFlow() {}

    @Suspendable
    @Override
    @NotNull
    public String call(@NotNull ClientRequestBody requestBody) {

        PackageApplesRequest request = requestBody.getRequestBodyAs(jsonMarshallingService, PackageApplesRequest.class);
        String appleDescription = request.getAppleDescription();
        int weight = request.getWeight();

        final NotaryInfo notaryInfo = notaryLookup.getNotaryServices().iterator().next();

        PublicKey myKey = memberLookup.myInfo().getLedgerKeys().get(0);

        // アウトプットになる BasketOfApples state を作成
        BasketOfApples basket = new BasketOfApples(
                appleDescription,
                myKey,
                myKey,
                weight
        );

        // トランザクションの作成
        UtxoSignedTransaction transaction = utxoLedgerService.createTransactionBuilder()
                .setNotary(notaryInfo.getName())
                .addOutputState(basket)
                .addCommand(new BasketOfApplesContract.PackBasket())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(myKey))
                .toSignedTransaction();

        try {
            return utxoLedgerService.finalize(transaction, Collections.emptyList()).getTransaction().getId().toString();
        } catch (Exception e) {
            return String.format("Flow failed, message: %s", e.getMessage());
        }
    }
}
