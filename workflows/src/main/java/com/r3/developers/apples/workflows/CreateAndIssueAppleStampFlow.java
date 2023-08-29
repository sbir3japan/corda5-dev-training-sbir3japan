//package com.r3.developers.apples.workflows;
//
//import com.r3.developers.apples.contracts.stamp.AppleStampContract;
//import com.r3.developers.apples.states.AppleStamp;
//import net.corda.v5.application.flows.ClientRequestBody;
//import net.corda.v5.application.flows.ClientStartableFlow;
//import net.corda.v5.application.flows.CordaInject;
//import net.corda.v5.application.flows.InitiatingFlow;
//import net.corda.v5.application.marshalling.JsonMarshallingService;
//import net.corda.v5.application.membership.MemberLookup;
//import net.corda.v5.application.messaging.FlowMessaging;
//import net.corda.v5.application.messaging.FlowSession;
//import net.corda.v5.base.annotations.Suspendable;
//import net.corda.v5.base.types.MemberX500Name;
//import net.corda.v5.ledger.common.NotaryLookup;
//import net.corda.v5.ledger.utxo.UtxoLedgerService;
//import net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction;
//import net.corda.v5.membership.NotaryInfo;
//import org.jetbrains.annotations.NotNull;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.security.PublicKey;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//import java.util.Objects;
//import java.util.UUID;
//
//@InitiatingFlow(protocol = "create-and-issue-apple-stamp")
//public class CreateAndIssueAppleStampFlow implements ClientStartableFlow {
//
//    private final static Logger log = LoggerFactory.getLogger(CreateAndIssueAppleStampFlow.class);
//
//    @CordaInject
//    private FlowMessaging flowMessaging;
//
//    @CordaInject
//    private JsonMarshallingService jsonMarshallingService;
//
//    @CordaInject
//    private MemberLookup memberLookup;
//
//    @CordaInject
//    private NotaryLookup notaryLookup;
//
//    @CordaInject
//    private UtxoLedgerService utxoLedgerService;
//
//    @NotNull
//    @Suspendable
//    @Override
//    public String call(ClientRequestBody requestBody) {
//
//        log.info("CreateAndIssueAppleStampFlow started.");
//
//        CreateAndIssueAppleStampRequest request = requestBody.getRequestBodyAs(jsonMarshallingService, CreateAndIssueAppleStampRequest.class);
//        String stampDescription = request.getStampDescription();
//        MemberX500Name holderName = request.getHolder();
//
//        NotaryInfo notaryInfo = notaryLookup.getNotaryServices().iterator().next();
//
//        PublicKey issuer = memberLookup.myInfo().getLedgerKeys().get(0);
//
//        PublicKey holder;
//        try {
//            holder = Objects.requireNonNull(memberLookup.lookup(holderName)).getLedgerKeys().get(0);
//        } catch (Exception e) {
//            throw new IllegalArgumentException(String.format("The holder %s does not exist within the network", holderName));
//        }
//
//        AppleStamp newStamp = new AppleStamp(
//                UUID.randomUUID(),
//                stampDescription,
//                issuer,
//                holder
//        );
//
//        UtxoSignedTransaction transaction = utxoLedgerService.createTransactionBuilder()
//                .setNotary(notaryInfo.getName())
//                .addOutputState(newStamp)
//                .addCommand(new AppleStampContract.AppleCommands.Issue())
//                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
//                .addSignatories(List.of(issuer, holder))
//                .toSignedTransaction();
//
//        FlowSession session = flowMessaging.initiateFlow(holderName);
//
//        try {
//            // Send the transaction and state to the counterparty and let them sign it
//            // Then notarise and record the transaction in both parties' vaults.
//            utxoLedgerService.finalize(transaction, List.of(session));
//
//            log.info("CreateAndIssueAppleStampFlow completed.");
//            return newStamp.getId().toString();
//        } catch (Exception e) {
//            return String.format("Flow failed, message: %s", e.getMessage());
//        }
//    }
//}