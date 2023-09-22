package com.r3.developers.apples.contracts;

import com.r3.corda.ledger.utxo.testing.ContractTest;
import com.r3.developers.apples.contracts.stamp.AppleStampContract;
import com.r3.developers.apples.states.AppleStamp;
import com.r3.developers.apples.states.BasketOfApples;
import net.corda.v5.ledger.utxo.StateRef;
import net.corda.v5.ledger.utxo.transaction.UtxoSignedTransaction;

import java.security.PublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public class CommonCommandTest extends ContractTest{

    /**
     * テスト用のledger鍵を生成
     * */
    PublicKey issuerKey = createSigningKey();
    PublicKey holderKey = createSigningKey();
    PublicKey farmKey = createSigningKey();
    PublicKey ownerKey = createSigningKey();

    AppleStamp appleStampOutputState = new AppleStamp(
            UUID.randomUUID(),
            "This is the apple stamp for the test",
            issuerKey,
            holderKey
    );

    AppleStamp appleStampBlankDesc = new AppleStamp(
            UUID.randomUUID(),
            "",
            issuerKey,
            holderKey
    );

    BasketOfApples basketOfApplesOutputState = new BasketOfApples(
            "This is the basket of apple for the test",
            farmKey,
            ownerKey,
            100
    );

    BasketOfApples basketOfApplesBlankDesc = new BasketOfApples(
            "",
            farmKey,
            ownerKey,
            100
    );

    BasketOfApples basketOfApplesInvalidWeight = new BasketOfApples(
            "This is the basket of apple for the test",
            farmKey,
            ownerKey,
            -1
    );


    StateRef createAppleInputStateRef() {

        AppleStamp existingAppleState = new AppleStamp(
                UUID.randomUUID(),
                "This is the apple stamp that is already existed in the ledger",
                issuerKey,
                holderKey
        );

        UtxoSignedTransaction existingTransaction = getLedgerService()
                .createTransactionBuilder()
                .addOutputState(existingAppleState)
                .addCommand(new AppleStampContract.AppleCommands.Issue())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        /* TxのOutput Stateが1つなので、indexを0でかえす */
        return new StateRef(existingTransaction.getId(), 0);
    }

    StateRef createBasketOfApplesStateRef() {

        BasketOfApples existingBasketOfApples = new BasketOfApples(
                "This is the apple stamp that is already existed in the ledger",
                farmKey,
                ownerKey,
                100
        );

        UtxoSignedTransaction existingTransaction = getLedgerService()
                .createTransactionBuilder()
                .addOutputState(existingBasketOfApples)
                .addCommand(new BasketOfApplesContract.BasketOfApplesCommands.PackBasket())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        /* TxのOutput Stateが1つなので、indexを0でかえす */
        return new StateRef(existingTransaction.getId(), 0);
    }

}
