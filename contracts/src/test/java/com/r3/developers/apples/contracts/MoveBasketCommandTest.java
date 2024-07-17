package com.r3.developers.apples.contracts;

import java.security.PublicKey;

public class MoveBasketCommandTest extends CommonCommandTest {

    /**
     * 2-①
     * BasketOfApplesのMoveコマンドにおいて、
     * TransactionのInput StateとしてBasketOfApplesが含まれていない場合
     * 検証に失敗することを確認.
     */
//    @Test
//    public void moveTransactionMustHaveOneInputs() {
//
//        UtxoSignedTransaction transaction = getLedgerService()
//                .createTransactionBuilder()
//                .addInputStates(createAppleInputStateRef())
//                .addOutputState(existingBasketOfApples.changeOwner(newHolderKey))
//                .addCommand(new AppleStampContract.AppleCommands.Redeem())
//                .addCommand(new BasketOfApplesContract.BasketOfApplesCommands.Move())
//                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
//                .addSignatories(List.of(aliceKey, daveKey))
//                .toSignedTransaction();
//
//        assertFailsWith(transaction, "Failed requirement: This transaction should include one BasketOfApples input state.");
//    }

    /**
     * 2-②
     * BasketOfApplesのMoveコマンドにおいて、
     * TransactionのOutput StateとしてBasketOfApplesが含まれていない場合
     * 検証に失敗することを確認.
     */
//    @Test
//    public void moveTransactionMustHaveOneOutputs() {
//
//        UtxoSignedTransaction transaction = getLedgerService()
//                .createTransactionBuilder()
//                .addInputStates(createAppleInputStateRef(), createBasketOfApplesStateRef())
//                .addCommand(new AppleStampContract.AppleCommands.Redeem())
//                .addCommand(new BasketOfApplesContract.BasketOfApplesCommands.Move())
//                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
//                .addSignatories(List.of(aliceKey, daveKey))
//                .toSignedTransaction();
//
//        assertFailsWith(transaction, "Failed requirement: This transaction should include one BasketOfApples output state.");
//    }

    /**
     * 2-③
     * BasketOfApplesのMoveコマンドにおいて、TransactionのInput StateとしてAppleStampStateが含まれていない場合、検証に失敗することを確認。
     */
//    @Test
//    public void moveTransactionMustHaveOneAppleInputs() {
//
//        UtxoSignedTransaction transaction = getLedgerService()
//                .createTransactionBuilder()
//                .addInputStates(createBasketOfApplesStateRef())
//                .addOutputState(existingBasketOfApples.changeOwner(newHolderKey))
//                .addCommand(new AppleStampContract.AppleCommands.Redeem())
//                .addCommand(new BasketOfApplesContract.BasketOfApplesCommands.Move())
//                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
//                .addSignatories(List.of(aliceKey, daveKey))
//                .toSignedTransaction();
//
//        assertFailsWith(transaction, "Failed requirement: This transaction should include one AppleStamp input state.");
//    }

    /**
     * 2-④
     * BasketOfApplesのMoveコマンドにおいて、
     * TransactionのInput Stateとして含まれるAppleStampStateのissuerとBasketOfApplesのfarmerが異なる場合
     * 検証に失敗することを確認。
     */
//    @Test
//    public void checkIssuerIsTheSameBetweenStampAndBasket() {
//
//        UtxoSignedTransaction transaction = getLedgerService()
//                .createTransactionBuilder()
//                .addInputStates(createOtherAppleInputStateRef(), createBasketOfApplesStateRef())
//                .addOutputState(existingBasketOfApples.changeOwner(newHolderKey))
//                .addCommand(new AppleStampContract.AppleCommands.Redeem())
//                .addCommand(new BasketOfApplesContract.BasketOfApplesCommands.Move())
//                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
//                .addSignatories(List.of(aliceKey, daveKey))
//                .toSignedTransaction();
//
//        assertFailsWith(transaction, "Failed requirement: The issuer of the Apple stamp should be the producing farm " +
//                "of this basket of apple");
//    }

    /**
     * 2-⑤
     * BasketOfApplesのMoveコマンドにおいて、
     * TransactionのInput StateのBasketとOutput StateのBasketOfApplesが変化していない場合
     * 検証に失敗することを確認。
     */
//    @Test
//    public void checkHoldersOfInputAndOutputMustChange() {
//
//        UtxoSignedTransaction transaction = getLedgerService()
//                .createTransactionBuilder()
//                .addInputStates(createAppleInputStateRef(), createBasketOfApplesStateRef())
//                .addOutputState(existingBasketOfApples)
//                .addCommand(new AppleStampContract.AppleCommands.Redeem())
//                .addCommand(new BasketOfApplesContract.BasketOfApplesCommands.Move())
//                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
//                .addSignatories(List.of(aliceKey, daveKey))
//                .toSignedTransaction();
//
//        assertFailsWith(transaction, "Failed requirement: The owner of the input and output should be changed.");
//    }
}
