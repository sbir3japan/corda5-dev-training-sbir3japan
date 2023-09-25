package com.r3.developers.apples.contracts;

import com.r3.developers.apples.contracts.stamp.AppleStampContract;
import com.r3.developers.apples.states.BasketOfApples;
import net.corda.v5.ledger.utxo.UtxoLedgerService;
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;
import net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BasketMoveCommandTest extends CommonCommandTest{

    /**
     * BasketOfApplesのMoveコマンドにおいて、TransactionのInput StateとしてBasketOfApplesが含まれていない場合、検証に失敗することを確認。
     */
    @Test
    public void moveTransactionMustHaveOneInputs() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addInputStates(createAppleInputStateRef())
                .addOutputState(existingBasketOfApples.changeOwner(newHolderKey))
                .addCommand(new AppleStampContract.AppleCommands.Redeem())
                .addCommand(new BasketOfApplesContract.BasketOfApplesCommands.Move())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        // Validate the output transaction is successful
        assertFailsWith(transaction, "Failed requirement: This transaction should include one BasketOfApples input state.");
    }

    /**
     * BasketOfApplesのMoveコマンドにおいて、TransactionのOutput StateとしてBasketOfApplesが含まれていない場合、検証に失敗することを確認。
     */
    @Test
    public void moveTransactionMustHaveOneOutputs() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addInputStates(createAppleInputStateRef(), createBasketOfApplesStateRef())
                .addCommand(new AppleStampContract.AppleCommands.Redeem())
                .addCommand(new BasketOfApplesContract.BasketOfApplesCommands.Move())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        // Validate the output transaction is successful
        assertFailsWith(transaction, "Failed requirement: This transaction should include one BasketOfApples output state.");
    }

    /**
     * BasketOfApplesのMoveコマンドにおいて、TransactionのInput StateとしてAppleStampStateが含まれていない場合、検証に失敗することを確認。
     */
    @Test
    public void mustIncludeSomeCommentsInStampDesc() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addInputStates(createBasketOfApplesStateRef())
                .addOutputState(existingBasketOfApples.changeOwner(newHolderKey))
                .addCommand(new AppleStampContract.AppleCommands.Redeem())
                .addCommand(new BasketOfApplesContract.BasketOfApplesCommands.Move())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        // Validate the output transaction is successful
        assertFailsWith(transaction, "Failed requirement: This transaction should include one AppleStamp input state.");
    }

    /**
     * BasketOfApplesのMoveコマンドにおいて、TransactionのInput Stateとして含まれるAppleStampStateのissuerとBasketOfApplesのfarmerが異なる場合、
     * 検証に失敗することを確認。
     */
    @Test
    public void checkIssuerIsTheSameBetweenStampAndBasket() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addInputStates(createOtherAppleInputStateRef(), createBasketOfApplesStateRef())
                .addOutputState(existingBasketOfApples.changeOwner(newHolderKey))
                .addCommand(new AppleStampContract.AppleCommands.Redeem())
                .addCommand(new BasketOfApplesContract.BasketOfApplesCommands.Move())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        // Validate the output transaction is successful
        assertFailsWith(transaction, "Failed requirement: The issuer of the Apple stamp should be the producing farm " +
                "of this basket of apple");
    }

    /**
     * BasketOfApplesのMoveコマンドにおいて、TransactionのInput StateのBasketとOutput StateのBasketOfApplesが変化していない場合、
     * 検証に失敗することを確認。
     */
    @Test
    public void checkHoldersOfInputAndOutputMustChange() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addInputStates(createAppleInputStateRef(), createBasketOfApplesStateRef())
                .addOutputState(existingBasketOfApples)
                .addCommand(new AppleStampContract.AppleCommands.Redeem())
                .addCommand(new BasketOfApplesContract.BasketOfApplesCommands.Move())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        // Validate the output transaction is successful
        assertFailsWith(transaction, "Failed requirement: The owner of the input and output should be changed.");
    }
    /**
     * 正しい情報を含むトランザクションが検証に成功することを確認
     */
    @Test
    public void succeedToVerifyTransaction() {

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addInputStates(createAppleInputStateRef(), createBasketOfApplesStateRef())
                .addOutputState(existingBasketOfApples.changeOwner(newHolderKey))
                .addCommand(new AppleStampContract.AppleCommands.Redeem())
                .addCommand(new BasketOfApplesContract.BasketOfApplesCommands.Move())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        // Validate the output transaction is successful
        assertVerifies(transaction);
    }
}
