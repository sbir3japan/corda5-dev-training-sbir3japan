package com.r3.developers.apples.workflows;

import com.r3.developers.apples.contracts.BasketOfApplesContract;
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

public class PackageApplesFlow implements ClientStartableFlow {

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

        String appleDescription;
        int weight;

        NotaryInfo notary;
        PublicKey myKey;
        // Building the output BasketOfApples state
        BasketOfApples basket;

        // Create the transaction
        UtxoSignedTransaction transaction;

        try {
            // Record the transaction, no sessions are passed in as the transaction is only being
            // recorded locally
            return null;
        } catch (Exception e) {
            return String.format("Flow failed, message: %s", e.getMessage());
        }
    }
}