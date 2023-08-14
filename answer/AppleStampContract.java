package com.r3.developers.apples.contracts;

import com.r3.developers.apples.states.AppleStamp;
import net.corda.v5.base.exceptions.CordaRuntimeException;
import net.corda.v5.ledger.utxo.Command;
import net.corda.v5.ledger.utxo.Contract;
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;

public class AppleStampContract implements Contract {

    public interface AppleCommands extends Command {
        class Issue implements AppleCommands {
        }

        class Redeem implements AppleCommands {
        }
    }

    @Override
    public void verify(UtxoLedgerTransaction transaction) {

        Command appleCommand = transaction.getCommands(AppleCommands.class).get(0);

        if(appleCommand.getClass() == AppleCommands.Issue.class) {

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
        else if(appleCommand.getClass() == AppleCommands.Redeem.class) {
            /* Check the size of input and output state */
            requireThat(transaction.getInputStates(AppleStamp.class).size() == 1,
                    "This transaction should include one AppleStamp input state.");
            requireThat(transaction.getOutputStates(AppleStamp.class).size() == 0,
                    "This transaction should not include an AppleStamp output state.");

            /* Check the content of output state*/
            AppleStamp input = transaction.getInputStates(AppleStamp.class).get(0);

            requireThat(transaction.getSignatories().contains(input.getHolder()),
                    "The holder of the input AppleStamp state must be a signatory to the transaction");
        }
        else {
            throw new CordaRuntimeException("Incorrect type of AppleStamp commands: " + transaction.getCommands().get(0).getClass());
        }
    }

    private void requireThat(boolean asserted, String errorMessage) {
        if(!asserted) {
            throw new CordaRuntimeException("Failed requirement: " + errorMessage);
        }
    }
}