package com.r3.developers.apples.workflows;

import net.corda.v5.application.flows.ClientRequestBody;
import net.corda.v5.application.flows.ClientStartableFlow;
import net.corda.v5.application.flows.CordaInject;
import net.corda.v5.application.marshalling.JsonMarshallingService;
import net.corda.v5.application.membership.MemberLookup;
import net.corda.v5.application.persistence.PagedQuery;
import net.corda.v5.ledger.utxo.UtxoLedgerService;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.time.Instant;
import java.util.Objects;

public class GetAppleStampByHolderFlow implements ClientStartableFlow {

    @CordaInject
    private JsonMarshallingService jsonMarshallingService;

    @CordaInject
    private MemberLookup memberLookup;

    @CordaInject
    private UtxoLedgerService utxoLedgerService;

    @NotNull
    @Override
    public String call(@NotNull ClientRequestBody requestBody) {

        GetAppleStampByHolderRequest request = requestBody.getRequestBodyAs(jsonMarshallingService, GetAppleStampByHolderRequest.class);

        PublicKey holder;
        try {
            holder = Objects.requireNonNull(memberLookup.lookup(request.getHolder())).getLedgerKeys().get(0);
        } catch (Exception e) {
            throw new IllegalArgumentException("The holder does not exist within the network");
        }

        int currentOffset = 0;

        PagedQuery.ResultSet<Integer> resultSet = utxoLedgerService.query("TOKEN_HOLDER_NAMED_QUERY", Integer.class) // instantiate the query
                .setOffset(0) // Start from the beginning
                .setLimit(1000) // Only return 1000 records
                .setParameter("holder", holder.toString()) // Set the parameter to a dummy value
                .setCreatedTimestampLimit(Instant.now()) // Set the timestamp limit to the current time
                .execute();

//        while (resultSet.results.isNotEmpty()) {
//            currentOffset += 1000;
//            query.setOffset(currentOffset);
//            resultSet = query.execute();
//        }


        return null;
    }
}
