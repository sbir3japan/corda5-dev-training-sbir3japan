package com.r3.developers.apples.contracts;

import net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BasketPackCommandTest extends CommonCommandTest{

    /**
     * BasketOfApplesのPackコマンドにおいて、TransactionのInput StateとしてBasketOfApplesが含まれてる場合、検証に失敗することを確認。
     */
    @Test
    public void packTransactionMustHaveNoInputs() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addInputState(createBasketOfApplesStateRef())
                .addOutputState(basketOfApplesOutputState)
                .addCommand(new BasketOfApplesContract.BasketOfApplesCommands.PackBasket())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        // Validate the output transaction is successful
        assertFailsWith(transaction, "Failed requirement: This transaction should not include an BasketOfApples input state.");
    }

    /**
     * BasketOfApplesのPackコマンドにおいて、TransactionのOutput StateとしてBasketOfApplesが複数含まれてる場合、検証に失敗することを確認。
     */
    @Test
    public void packTransactionMustHaveOneOutputs() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addOutputState(basketOfApplesOutputState)
                .addOutputState(basketOfApplesOutputState)
                .addCommand(new BasketOfApplesContract.BasketOfApplesCommands.PackBasket())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        // Validate the output transaction is successful
        assertFailsWith(transaction, "Failed requirement: This transaction should include one BasketOfApples output state.");
    }

    /**
     * BasketOfApplesのPackコマンドにおいて、BasketOfApplesのStampDescが空白の場合、検証に失敗することを確認。
     */
    @Test
    public void mustIncludeSomeCommentsInStampDesc() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addOutputState(basketOfApplesBlankDesc)
                .addCommand(new BasketOfApplesContract.BasketOfApplesCommands.PackBasket())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        // Validate the output transaction is successful
        assertFailsWith(transaction, "Failed requirement: The output BasketOfApples state should have " +
                "clear description of Apple product");
    }

    /**
     * BasketOfApplesのPackコマンドにおいて、BasketOfApplesのWeightが0より小さい場合、検証に失敗することを確認。
     */
    @Test
    public void checkWeightIsGreaterThanZero() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addOutputState(basketOfApplesInvalidWeight)
                .addCommand(new BasketOfApplesContract.BasketOfApplesCommands.PackBasket())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        // Validate the output transaction is successful
        assertFailsWith(transaction, "Failed requirement: The output BasketOfApples state should have " +
                "non zero weight");
    }
    /**
     * 正しい情報を含むトランザクションが検証に成功することを確認
     */
    @Test
    public void succeedToVerifyTransaction() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addOutputState(basketOfApplesOutputState)
                .addCommand(new BasketOfApplesContract.BasketOfApplesCommands.PackBasket())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        // Validate the output transaction is successful
        assertVerifies(transaction);
    }
}
