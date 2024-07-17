package com.r3.developers.apples.workflows;

import com.r3.developers.apples.contracts.applestamp.AppleStampContract;
import com.r3.developers.apples.states.AppleStamp;
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
import net.corda.v5.ledger.utxo.UtxoLedgerService;
import net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction;
import net.corda.v5.membership.NotaryInfo;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * issuerがholderにりんごの引換券を発行するFlow
 */

// ① アノテーションとクラス継承
@InitiatingFlow(protocol = "create-and-issue-apple-stamp")
public class CreateAndIssueAppleStampFlow implements ClientStartableFlow {

    private final static Logger log = LoggerFactory.getLogger(CreateAndIssueAppleStampFlow.class);

    // ② サービス定義
    @CordaInject
    public FlowMessaging flowMessaging;

    @CordaInject
    public JsonMarshallingService jsonMarshallingService;

    @CordaInject
    public MemberLookup memberLookup;

    @CordaInject
    NotaryLookup notaryLookup;

    @CordaInject
    UtxoLedgerService utxoLedgerService;

    // 空のコンストラクタ. javaのみ必要です.
    public CreateAndIssueAppleStampFlow() {}

    // ③ call関数
    @Suspendable
    @Override
    @NotNull
    public String call(@NotNull ClientRequestBody requestBody) {

        log.info("CreateAndIssueAppleStampFlow started.");

        // ④ 準備
        // ④-1 Requestの処理
        CreateAndIssueAppleStampRequest request = requestBody.getRequestBodyAs(jsonMarshallingService, CreateAndIssueAppleStampRequest.class);
        String stampDescription = request.getStampDescription();
        MemberX500Name holderName = request.getHolder();

        // ④-2 Notaryの準備
        final NotaryInfo notaryInfo = notaryLookup.getNotaryServices().iterator().next();

        // ④-2 鍵の準備
        PublicKey issuer = memberLookup.myInfo().getLedgerKeys().get(0);

        PublicKey holder;
        try {
            holder = Objects.requireNonNull(memberLookup.lookup(holderName)).getLedgerKeys().get(0);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("The holder %s has no ledger key", holderName));
        }

        // ④-3 Stateの構築
        AppleStamp newStamp = new AppleStamp(
                UUID.randomUUID(),
                stampDescription,
                issuer,
                holder
        );

        // ⑤ Transaction構築
        UtxoSignedTransaction transaction = utxoLedgerService.createTransactionBuilder()
                .setNotary(notaryInfo.getName())
                .addOutputState(newStamp)
                .addCommand(new AppleStampContract.AppleCommands.Issue())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuer, holder))
                .toSignedTransaction();

        // ⑥ Flowセッション構築
        FlowSession session = flowMessaging.initiateFlow(holderName);

        try {
            // ⑦ 通信 & 署名 & Notary & 格納
            utxoLedgerService.finalize(transaction, List.of(session));

            log.info("CreateAndIssueAppleStampFlow completed.");

            // ⑧ 返り値の構築
            return "stampId: " + newStamp.getId().toString();
        } catch (Exception e) {
            return String.format("Flow failed, message: %s", e.getMessage());
        }
    }
}
