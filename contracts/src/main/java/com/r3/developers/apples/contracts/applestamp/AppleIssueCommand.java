package com.r3.developers.apples.contracts.applestamp;

import com.r3.developers.apples.states.AppleStamp;
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;

public class AppleIssueCommand extends AppleStampContractCommand {

    @Override
    public void verify(UtxoLedgerTransaction transaction) {
        onVerify(transaction);
    }

    protected void onVerify(UtxoLedgerTransaction transaction) {

        /* Check the size of input and output state */
        requireThat(transaction.getInputStates(AppleStamp.class).size() == 0,
                "This transaction should not include an AppleStamp input state.");
        requireThat(transaction.getOutputStates(AppleStamp.class).size() == 1,
                "This transaction should include one AppleStamp output state.");

        /* Check the content of output state*/
        AppleStamp output = transaction.getOutputStates(AppleStamp.class).get(0);

        requireThat(!(output.getStampDesc().isBlank()),
                "The output AppleStamp state should have clear description of the type of redeemable goods");

    }
}
