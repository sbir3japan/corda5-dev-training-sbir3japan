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
     * テスト用のledger鍵を生成。
     * issuerKey: AppleStampのissuerとBasketOfApplesのfarmerの両方で使う。
     * holderKeyKey: AppleStampのholderKeyとBasketOfApplesのownerの両方で使う。
     * otherIssuerKey: issuerと同じでない鍵。AppleStampのissuerとBasketOfApplesのownerは同一であることが期待される。
     * newHolderKey: BasketOfApplesの移転先の公開鍵
     * */
    PublicKey issuerKey = createSigningKey();
    PublicKey holderKey = createSigningKey();
    PublicKey otherIssuerKey = createSigningKey();
    PublicKey newHolderKey = createSigningKey();


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
            issuerKey,
            holderKey,
            100
    );

    BasketOfApples basketOfApplesBlankDesc = new BasketOfApples(
            "",
            issuerKey,
            holderKey,
            100
    );

    BasketOfApples basketOfApplesInvalidWeight = new BasketOfApples(
            "This is the basket of apple for the test",
            issuerKey,
            holderKey,
            -1
    );

    BasketOfApples existingBasketOfApples = new BasketOfApples(
            "This is the apple stamp that is already existed in the ledger",
            issuerKey,
            holderKey,
            100
    );

    AppleStamp existingAppleState = new AppleStamp(
            UUID.randomUUID(),
            "This is the apple stamp that is already existed in the ledger",
            issuerKey,
            holderKey
    );

    AppleStamp otherIssuerAppleState = new AppleStamp(
            UUID.randomUUID(),
            "This is the apple stamp that is issued by a different issuer",
            otherIssuerKey,
            holderKey
    );

    StateRef createAppleInputStateRef() {

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

    StateRef createOtherAppleInputStateRef() {

        UtxoSignedTransaction existingTransaction = getLedgerService()
                .createTransactionBuilder()
                .addOutputState(otherIssuerAppleState)
                .addCommand(new AppleStampContract.AppleCommands.Issue())
                .setTimeWindowUntil(Instant.now().plus(1, ChronoUnit.DAYS))
                .addSignatories(List.of(issuerKey, holderKey))
                .toSignedTransaction();

        /* TxのOutput Stateが1つなので、indexを0でかえす */
        return new StateRef(existingTransaction.getId(), 0);
    }

    StateRef createBasketOfApplesStateRef() {

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
