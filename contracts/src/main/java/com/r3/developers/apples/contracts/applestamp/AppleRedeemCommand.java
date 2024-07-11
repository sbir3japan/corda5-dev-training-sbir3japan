package com.r3.developers.apples.contracts.applestamp;

import com.r3.developers.apples.states.AppleStamp;
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;

public class AppleRedeemCommand extends AppleStampContractCommand {

    @Override
    public void verify(UtxoLedgerTransaction transaction) {
        onVerify(transaction);
    }

    protected void onVerify(UtxoLedgerTransaction transaction) {

        // インプットとアウトプットの個数の確認.
        requireThat(transaction.getInputStates(AppleStamp.class).size() == 1,
                "This transaction should include one AppleStamp input state.");
        requireThat(transaction.getOutputStates(AppleStamp.class).size() == 0,
                "This transaction should not include an AppleStamp output state.");

        // Txの署名者にインプットのholderが含まれていることを確認.
        AppleStamp input = transaction.getInputStates(AppleStamp.class).get(0);
        requireThat(transaction.getSignatories().contains(input.getHolder()),
                "The holder of the input AppleStamp state must be a signatory to the transaction.");
    }
}
