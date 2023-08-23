package com.r3.developers.apples.contracts;

import com.r3.developers.apples.states.AppleStamp;
import com.r3.developers.apples.states.BasketOfApples;
import net.corda.v5.base.exceptions.CordaRuntimeException;
import net.corda.v5.ledger.utxo.Command;
import net.corda.v5.ledger.utxo.Contract;
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;

public class BasketOfApplesContract implements Contract {

    public interface BasketOfApplesCommands extends Command {
        class PackBasket implements BasketOfApplesCommands {
        }

        class Redeem implements BasketOfApplesCommands {
        }
    }

    @Override
    public void verify(UtxoLedgerTransaction transaction) {

        Command basketOfApplesCommand = transaction.getCommands(BasketOfApplesContract.BasketOfApplesCommands.class).get(0);

        if(basketOfApplesCommand.getClass() == BasketOfApplesCommands.PackBasket.class) {

            /* Check the size of input and output state */
            requireThat(transaction.getInputStates(BasketOfApples.class).size() == 0,
                    "This transaction should not include an BasketOfApples input state.");
            requireThat(transaction.getOutputStates(BasketOfApples.class).size() == 1,
                    "This transaction should include one BasketOfApples output state.");

            /* Check the content of output state*/
            BasketOfApples output = transaction.getOutputStates(BasketOfApples.class).get(0);

            requireThat(!(output.getDescription().isBlank()),
                    "The output BasketOfApples state should have clear description of Apple product");

            requireThat(output.getWeight() > 0 ,
                "The output BasketOfApples state should have non zero weight"
            );
        }
        else if(basketOfApplesCommand.getClass() == BasketOfApplesCommands.Redeem.class) {
            /* Check the size of input and output state */
            requireThat(transaction.getInputStates(BasketOfApples.class).size() == 1,
                    "This transaction should include one BasketOfApples input state.");

            /* Check the size of input state of AppleStamp.
            This smart contract can combine AppleStamp and BasketOfApples reimbursements.*/
            requireThat(transaction.getInputStates(AppleStamp.class).size() == 1,
                    "This transaction should include one AppleStamp input state.");

            /* Check the content of output state*/
            AppleStamp inputAppleStamp = transaction.getInputStates(AppleStamp.class).get(0);
            BasketOfApples inputBasketOfApples = transaction.getInputStates(BasketOfApples.class).get(0);

            requireThat(inputAppleStamp.getIssuer().equals(inputBasketOfApples.getFarm()),
                    "The issuer of the Apple stamp should be the producing farm of this basket of apple");
        }
        else {
            throw new CordaRuntimeException("Incorrect type of BasketOfApples commands: " + transaction.getCommands().get(0).getClass());
        }
    }

    private void requireThat(boolean asserted, String errorMessage) {
        if(!asserted) {
            throw new CordaRuntimeException("Failed requirement: " + errorMessage);
        }
    }
}