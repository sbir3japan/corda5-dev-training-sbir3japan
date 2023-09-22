package com.r3.developers.apples.contracts;

import com.r3.developers.apples.contracts.stamp.AppleStampContract;
import net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class AppleRedeemCommandTest extends CommonCommandTest{

    /**
     * Input Stateが含まれていないことを確認。
     */
    @Test
    public void issueTransactionMustHaveOneInput() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addInputState(createAppleInputStateRef())
                .addInputState(createAppleInputStateRef())
                .addCommand(new AppleStampContract.AppleCommands.Redeem())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        // Validate the output transaction is successful
        assertFailsWith(transaction, "Failed requirement: This transaction should include one AppleStamp input state.");
    }

    /**
     * Output Stateが一つだけ含まれていることを確認。
     */
    @Test
    public void issueTransactionMustHaveNoOutputs() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addCommand(new AppleStampContract.AppleCommands.Redeem())
                .addInputState(createAppleInputStateRef())
                .addOutputState(appleStampOutputState)
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        // Validate the output transaction is successful
        assertFailsWith(transaction, "Failed requirement: This transaction should not include an AppleStamp output state.");
    }

    /**
     * 署名者として、input stateのholderが含まれていることを確認。
     */
    @Test
    public void mustIncludeSignOfHolder() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addInputState(createAppleInputStateRef())
                .addCommand(new AppleStampContract.AppleCommands.Redeem())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey))
                .toSignedTransaction();

        // Validate the output transaction is successful
        assertFailsWith(transaction, "Failed requirement: The holder of the input AppleStamp state must " +
                "be a signatory to the transaction.");
    }

    /**
     * 正しい情報を含むトランザクションが検証に成功することを確認
     */
    @Test
    public void succeedToVerifyTransaction() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addInputState(createAppleInputStateRef())
                .addCommand(new AppleStampContract.AppleCommands.Redeem())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        // Validate the output transaction is successful
        assertVerifies(transaction);
    }

}
