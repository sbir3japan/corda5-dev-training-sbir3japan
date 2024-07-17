package com.r3.developers.apples.contracts;

import com.r3.corda.ledger.utxo.testing.ContractTest;
import com.r3.developers.apples.TestKeyUtils;
import com.r3.developers.apples.contracts.applestamp.AppleStampContract;
import com.r3.developers.apples.contracts.basketofapples.BasketOfApplesContract;
import com.r3.developers.apples.states.AppleStamp;
import com.r3.developers.apples.states.BasketOfApples;
import net.corda.v5.base.types.MemberX500Name;
import net.corda.v5.ledger.utxo.StateRef;
import net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction;

import java.security.PublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public class CommonCommandTest extends ContractTest {

    // 正常なAppleStampのOutputState
    AppleStamp appleStampOutputState = new AppleStamp(
            UUID.randomUUID(),
            "This is the apple stamp for the test",
            aliceKey,
            daveKey
    );

    // AppleStampのDescriptionが空のOutputState
    AppleStamp appleStampBlankDesc = new AppleStamp(
            UUID.randomUUID(),
            "",
            aliceKey,
            daveKey
    );

    // InputStateを作成するためのすでに存在するAppleStamp
    private AppleStamp existingAppleState = new AppleStamp(
            UUID.randomUUID(),
            "This is the apple stamp that is already existed in the ledger",
            aliceKey,
            daveKey
    );

    /**
     * AppleStampのInputStateの構築をします。
     * UtxoSignedTransactionを作成し、
     * 生成したtxからtx構築時のinputState部分に含めるStateRefを生成します。
     * @return inputState
     */
    StateRef createAppleInputStateRef() {
        UtxoSignedTransaction existingTransaction = getLedgerService()
                .createTransactionBuilder()
                .addOutputState(existingAppleState)
                .addCommand(new AppleStampContract.AppleCommands.Issue())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(aliceKey, daveKey))
                .toSignedTransaction();

        /* TxのOutput Stateが1つなので、indexを0でかえす */
        return new StateRef(existingTransaction.getId(), 0);
    }

    // IssuerがAliceではないAppleStampのOutputState
    PublicKey otherIssuer = createSigningKey();
    AppleStamp otherIssuerAppleState = new AppleStamp(
            UUID.randomUUID(),
            "This is the apple stamp that is issued by a different issuer",
            otherIssuer,
            daveKey
    );

    /**
     * IssuerがAliceではないAppleStampのInputStateの構築をします。
     * @return　inputState
     */
    StateRef createOtherAppleInputStateRef() {

        UtxoSignedTransaction existingTransaction = getLedgerService()
                .createTransactionBuilder()
                .addOutputState(otherIssuerAppleState)
                .addCommand(new AppleStampContract.AppleCommands.Issue())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(aliceKey, daveKey))
                .toSignedTransaction();

        /* TxのOutput Stateが1つなので、indexを0でかえす */
        return new StateRef(existingTransaction.getId(), 0);
    }

        BasketOfApples basketOfApplesOutputState = new BasketOfApples(
            "This is the basket of apple for the test",
            aliceKey,
            aliceKey,
            100
    );

    BasketOfApples basketOfApplesBlankDesc = new BasketOfApples(
            "",
            aliceKey,
            aliceKey,
            100
    );

    BasketOfApples basketOfApplesInvalidWeight = new BasketOfApples(
            "This is the basket of apple for the test",
            aliceKey,
            aliceKey,
            -1
    );

    BasketOfApples existingBasketOfApples = new BasketOfApples(
            "This is the apple stamp that is already existed in the ledger",
            aliceKey,
            aliceKey,
            100
    );

    /**
     * BasketOfApplesのOutputStateの構築をします。
     */
    StateRef createBasketOfApplesStateRef() {

        UtxoSignedTransaction existingTransaction = getLedgerService()
                .createTransactionBuilder()
                .addOutputState(existingBasketOfApples)
                .addCommand(new BasketOfApplesContract.PackBasket())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(aliceKey))
                .toSignedTransaction();

        /* TxのOutput Stateが1つなので、indexを0でかえす */
        return new StateRef(existingTransaction.getId(), 0);
    }

}
