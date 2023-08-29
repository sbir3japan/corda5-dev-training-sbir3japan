package com.r3.developers.apples.workflows;

import com.r3.developers.apples.states.AppleStamp;
import net.corda.v5.application.flows.ClientRequestBody;
import net.corda.v5.application.flows.ClientStartableFlow;
import net.corda.v5.application.flows.CordaInject;
import net.corda.v5.application.marshalling.JsonMarshallingService;
import net.corda.v5.application.membership.MemberLookup;
import net.corda.v5.application.persistence.PagedQuery;
import net.corda.v5.base.annotations.Suspendable;
import net.corda.v5.ledger.utxo.StateAndRef;
import net.corda.v5.ledger.utxo.UtxoLedgerService;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GetStampIdOfAppleStampByHolderFlow implements ClientStartableFlow {

    String APPLE_STAMP_FULL_PATH = "com.r3.developers.apples.states.AppleStamp";
    @CordaInject
    private JsonMarshallingService jsonMarshallingService;

    @CordaInject
    private MemberLookup memberLookup;

    @CordaInject
    private UtxoLedgerService utxoLedgerService;

    @NotNull
    @Suspendable
    @Override
    public String call(@NotNull ClientRequestBody requestBody) {

        GetAppleStampByHolderRequest request = requestBody.getRequestBodyAs(jsonMarshallingService, GetAppleStampByHolderRequest.class);

        PublicKey holder;
        try {
            holder = Objects.requireNonNull(memberLookup.lookup(request.getHolder())).getLedgerKeys().get(0);
        } catch (Exception e) {
            throw new IllegalArgumentException("The holder does not exist within the network");
        }


        PagedQuery.ResultSet<?> resultSet = utxoLedgerService.query("TOKEN_HOLDER_NAMED_QUERY", StateAndRef.class) // instantiate the query
                .setOffset(0) // Start from the beginning
                .setLimit(1000) // Only return 1000 records
                .setParameter("holder", holder.toString())
                .setCreatedTimestampLimit(Instant.now()) // Set the timestamp limit to the current time
                .execute();

        List<StateAndRef<AppleStamp>> results = (List<StateAndRef<AppleStamp>>) resultSet.getResults();
        List<String> appleStampId = new ArrayList<>();

        results.forEach( stateAndRefOfStamp ->
                appleStampId.add(stateAndRefOfStamp.getState().getContractState().getId().toString())
                );

        return jsonMarshallingService.format(appleStampId);
    }
}
