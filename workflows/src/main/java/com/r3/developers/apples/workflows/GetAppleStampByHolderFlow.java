package com.r3.developers.apples.workflows;

import com.r3.developers.apples.query.json.AppleStampJson;
import com.r3.developers.apples.query.json.BasketOfApplesJson;
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
import net.corda.v5.membership.MemberInfo;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.r3.developers.apples.query.factory.AppleStampVaultNamedQueryFactory.TOKEN_HOLDER_NAMED_QUERY;

/**
 * GetAppleStampByHolderFlow
 * Holderを引数にAppleStampをクエリし、該当するStampをListにして返す。
* */
public class GetAppleStampByHolderFlow implements ClientStartableFlow {

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

        PagedQuery.ResultSet<?> resultSet = utxoLedgerService.query(TOKEN_HOLDER_NAMED_QUERY, StateAndRef.class) // instantiate the query
                .setOffset(0) // Start from the beginning
                .setLimit(1000) // Only return 1000 records
                .setParameter("holder", holder.toString())
                .setCreatedTimestampLimit(Instant.now()) // Set the timestamp limit to the current time
                .execute();

        List<StateAndRef<AppleStamp>> results = (List<StateAndRef<AppleStamp>>) resultSet.getResults();
        List<AppleStampJson> listOfDeserializedStamp = new ArrayList<>();


        for (StateAndRef<AppleStamp> stateAndRefOfStamp : results) {

            AppleStamp appleStamp = stateAndRefOfStamp.getState().getContractState();
            MemberInfo IssuerInfo = memberLookup.lookup(appleStamp.getIssuer());
            MemberInfo holderInfo = memberLookup.lookup(appleStamp.getHolder());

            listOfDeserializedStamp.add(new AppleStampJson(
                    appleStamp.getId().toString(),
                    appleStamp.getStampDesc(),
                    IssuerInfo.getName().toString(),
                    holderInfo.getName().toString()
            ));
        }

        return jsonMarshallingService.format(listOfDeserializedStamp);

    }
}
