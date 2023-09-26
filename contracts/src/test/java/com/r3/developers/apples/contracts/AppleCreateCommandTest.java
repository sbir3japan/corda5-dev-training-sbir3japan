package com.r3.developers.apples.contracts;

import com.r3.developers.apples.contracts.stamp.AppleStampContract;
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
     * 正しい情報を含むトランザクションが検証に成功することを確認
     */
    @Test
    public void succeedToVerifyTransaction() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addOutputState(appleStampOutputState)
                .addCommand(new AppleStampContract.AppleCommands.Issue())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        assertVerifies(transaction);
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
