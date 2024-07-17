package com.r3.developers.apples.contracts;

import com.r3.developers.apples.contracts.basketofapples.BasketOfApplesContract;
import net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PackBasketCommandTest extends CommonCommandTest {

    /**
     * 1-⓪
     * BasketOfApplesContractのフレームワークがCommandごとに処理を振り分けて、
     * 期待しない種類のCommandsの場合、検証に失敗することを確認。
     */
    @Test
    public void setupContractFramework() {

        /* BasketOfApplesCommandsを実装したダミーのコマンドを定義。 */
        class DummyCommand implements BasketOfApplesContract.BasketOfApplesContractCommands { }

        UtxoSignedTransaction transaction = getLedgerService()
                .createTransactionBuilder()
                .addOutputState(basketOfApplesOutputState)
                .addCommand(new DummyCommand())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(aliceKey))
                .toSignedTransaction();

        assertFailsWith(transaction, "Unrecognised command type.");
    }

    /**
     * 1-①
     * BasketOfApplesのPackコマンドにおいて、
     * TransactionのInput StateとしてBasketOfApplesが含まれてる場合
     * 検証に失敗することを確認。
     * さらに、Exceptionメッセージのテンプレート"Failed Requirement: "が実装されていることを確認。
     */
//    @Test
//    public void packTransactionMustHaveNoInputs() {
//
//        UtxoSignedTransaction transaction = getLedgerService()
//                .createTransactionBuilder()
//                .addInputState(createBasketOfApplesStateRef())
//                .addOutputState(basketOfApplesOutputState)
//                .addCommand(new BasketOfApplesContract.PackBasket())
//                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
//                .addSignatories(List.of(aliceKey))
//                .toSignedTransaction();
//
//        assertFailsWith(transaction, "Failed requirement: This transaction should not include an BasketOfApples input state.");
//    }

    /**
     * 1-②
     * BasketOfApplesのPackコマンドにおいて、
     * TransactionのOutput StateとしてBasketOfApplesが複数含まれてる場合
     * 検証に失敗することを確認。
     */
//    @Test
//    public void packTransactionMustHaveOneOutputs() {
//
//        UtxoSignedTransaction transaction = getLedgerService()
//                .createTransactionBuilder()
//                .addOutputState(basketOfApplesOutputState)
//                .addOutputState(basketOfApplesOutputState)
//                .addCommand(new BasketOfApplesContract.PackBasket())
//                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
//                .addSignatories(List.of(aliceKey))
//                .toSignedTransaction();
//
//        assertFailsWith(transaction, "Failed requirement: This transaction should include one BasketOfApples output state.");
//    }

    /**
     * 1-③
     * BasketOfApplesのPackコマンドにおいて、
     * BasketOfApplesのStampDescが空白の場合
     * 検証に失敗することを確認。
     */
//    @Test
//    public void mustIncludeSomeCommentsInStampDesc() {
//
//        UtxoSignedTransaction transaction = getLedgerService()
//                .createTransactionBuilder()
//                .addOutputState(basketOfApplesBlankDesc)
//                .addCommand(new BasketOfApplesContract.PackBasket())
//                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
//                .addSignatories(List.of(aliceKey))
//                .toSignedTransaction();
//
//        assertFailsWith(transaction, "Failed requirement: The output BasketOfApples state should have " +
//                "clear description of Apple product.");
//    }

    /**
     * 1-④
     * BasketOfApplesのPackコマンドにおいて、
     * BasketOfApplesのWeightが0より小さい場合
     * 検証に失敗することを確認。
     */
//    @Test
//    public void checkWeightIsGreaterThanZero() {
//
//        UtxoSignedTransaction transaction = getLedgerService()
//                .createTransactionBuilder()
//                .addOutputState(basketOfApplesInvalidWeight)
//                .addCommand(new BasketOfApplesContract.PackBasket())
//                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
//                .addSignatories(List.of(aliceKey))
//                .toSignedTransaction();
//
//        assertFailsWith(transaction, "Failed requirement: The output BasketOfApples state should have " +
//                "non zero weight");
//    }
}
