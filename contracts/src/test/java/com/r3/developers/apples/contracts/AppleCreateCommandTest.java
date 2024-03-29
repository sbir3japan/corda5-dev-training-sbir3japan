package com.r3.developers.apples.contracts;

import com.r3.developers.apples.contracts.stamp.AppleContractCommand;
import com.r3.developers.apples.contracts.stamp.AppleStampContract;
import com.r3.developers.apples.states.AppleStamp;
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;
import net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * AppleCreateCommandTest: AppleStampContractのIssueコマンドの検証をするためのテストコード。
 * */
public class AppleCreateCommandTest extends CommonCommandTest{

    /**
     * AppleStampのContractのフレームワークとして以下が実装されていることを確認。
     * ①: Exceptionメッセージのテンプレート"Failed Requirement: "が実装されていることを確認
     * ②: BasketOfApplesCommandsオブジェクトの取得、設定
     * ③: Commandごとに処理を振り分けて期待しない種類のCommandsの場合、検証に失敗することを確認。
     */
    @Test
    public void setupContractFramework() {

        /* BasketOfApplesCommandsを実装したダミーのコマンドを定義。 */
        class DummyCommand implements AppleStampContract.AppleCommands { }

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addOutputState(appleStampOutputState)
                .addCommand(new DummyCommand())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        assertFailsWith(transaction, "Incorrect type of BasketOfApples commands: " +
                DummyCommand.class);
    }


    /**
     * AppleStampのCreateコマンドにおいて、TransactionのInput StateとしてAppleStampが含まれてる場合、検証に失敗することを確認。
     */
    @Test
    public void issueTransactionMustHaveNoInputs() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addInputState(createAppleInputStateRef())
                .addOutputState(appleStampOutputState)
                .addCommand(new AppleStampContract.AppleCommands.Issue())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        assertFailsWith(transaction, "Failed requirement: This transaction should not include an AppleStamp input state.");
    }

    /**
     * AppleStampのCreateコマンドにおいて、TransactionのOutput StateとしてAppleStampが複数含まれてる場合、検証に失敗することを確認。
     */
    @Test
    public void issueTransactionMustHaveOneOutputs() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addOutputState(appleStampOutputState)
                .addOutputState(appleStampOutputState)
                .addCommand(new AppleStampContract.AppleCommands.Issue())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        assertFailsWith(transaction, "Failed requirement: This transaction should include one AppleStamp output state.");
    }

    /**
     * AppleStampのCreateコマンドにおいて、AppleStampのStampDescが空白の場合、検証に失敗することを確認。
     */
    @Test
    public void mustIncludeSomeCommentsInStampDesc() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addOutputState(appleStampBlankDesc)
                .addCommand(new AppleStampContract.AppleCommands.Issue())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        assertFailsWith(transaction, "Failed requirement: The output AppleStamp state should have clear description " +
                "of the type of redeemable goods");
    }

    /**
     * Transactionの中にInput StateもOutput Stateも含まれない場合、検証が失敗することを期待するが成功してしまう挙動確認。
     * (ツールのバグの検証)
     */
    @Test
    public void passTheVerificationEvenThoughNoStateInTransaction() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addCommand(new AppleStampContract.AppleCommands.Issue())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        assertVerifies(transaction);
    }

}
