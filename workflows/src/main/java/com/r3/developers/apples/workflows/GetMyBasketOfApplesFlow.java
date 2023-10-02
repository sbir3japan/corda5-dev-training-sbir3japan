//package com.r3.developers.apples.workflows;
//
//import com.r3.developers.apples.query.json.BasketOfApplesJson;
//import com.r3.developers.apples.states.BasketOfApples;
//import net.corda.v5.application.flows.ClientRequestBody;
//import net.corda.v5.application.flows.ClientStartableFlow;
//import net.corda.v5.application.flows.CordaInject;
//import net.corda.v5.application.marshalling.JsonMarshallingService;
//import net.corda.v5.application.membership.MemberLookup;
//import net.corda.v5.application.persistence.PagedQuery;
//import net.corda.v5.base.annotations.Suspendable;
//import net.corda.v5.ledger.utxo.StateAndRef;
//import net.corda.v5.ledger.utxo.UtxoLedgerService;
//import net.corda.v5.membership.MemberInfo;
//import org.jetbrains.annotations.NotNull;
//
//import java.security.PublicKey;
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import static com.r3.developers.apples.query.factory.BasketOfApplesVaultNamedQueryFactory.TOKEN_OWNER_NAMED_QUERY;
//
///**
// * GetMyBasketOfApplesFlow
// * 実行NodeがOwnerのBasketOfApplesを取得します。
// * */
//public class GetMyBasketOfApplesFlow implements ClientStartableFlow {
//
//    @CordaInject
//    private JsonMarshallingService jsonMarshallingService;
//
//    @CordaInject
//    private MemberLookup memberLookup;
//
//    @CordaInject
//    private UtxoLedgerService utxoLedgerService;
//
//    @NotNull
//    @Suspendable
//    @Override
//    public String call(@NotNull ClientRequestBody requestBody) {
//
//        /* 自分自身のNode情報のクエリの条件とするため、引数は特になし。 */
//        PublicKey owner;
//        try {
//            owner = Objects.requireNonNull(memberLookup.myInfo()).getLedgerKeys().get(0);
//        } catch (Exception e) {
//            throw new IllegalArgumentException("The owner does not exist within the network");
//        }
//
//        PagedQuery.ResultSet<?> resultSet = utxoLedgerService.query(TOKEN_OWNER_NAMED_QUERY, StateAndRef.class) // instantiate the query
//                .setOffset(0) // Start from the beginning
//                .setLimit(1000) // Only return 1000 records
//                .setParameter("owner", owner.toString())
//                .setCreatedTimestampLimit(Instant.now()) // Set the timestamp limit to the current time
//                .execute();
//
//        List<StateAndRef<BasketOfApples>> results = (List<StateAndRef<BasketOfApples>>) resultSet.getResults();
//        List<BasketOfApplesJson> listOfDeserializedStamp = new ArrayList<>();
//
//        for (StateAndRef<BasketOfApples> stateAndRefOfBaskets : results) {
//
//            BasketOfApples basketOfApples = stateAndRefOfBaskets.getState().getContractState();
//            MemberInfo farmInfo = memberLookup.lookup(basketOfApples.getFarm());
//            MemberInfo ownerInfo = memberLookup.lookup(basketOfApples.getOwner());
//
//            listOfDeserializedStamp.add(new BasketOfApplesJson(
//                    basketOfApples.getDescription(),
//                    farmInfo.getName().toString(),
//                    ownerInfo.getName().toString(),
//                    basketOfApples.getWeight()
//            ));
//        }
//
//        return jsonMarshallingService.format(listOfDeserializedStamp);
//    }
//}