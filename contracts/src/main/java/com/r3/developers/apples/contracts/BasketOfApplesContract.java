package com.r3.developers.apples.contracts;

import com.r3.developers.apples.states.AppleStamp;
import com.r3.developers.apples.states.BasketOfApples;
import net.corda.v5.base.exceptions.CordaRuntimeException;
import net.corda.v5.ledger.utxo.Command;
import net.corda.v5.ledger.utxo.Contract;
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;
<<<<<<< HEAD

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
            requireThat(transaction.getOutputStates(BasketOfApples.class).size() == 0,
                    "This transaction should not include a BasketOfApples output state.");

            /* Check the size of input state of AppleStamp.
            This smart contract can combine AppleStamp and BasketOfApples reimbursements.*/
            requireThat(transaction.getInputStates(AppleStamp.class).size() == 1,
                    "This transaction should include one AppleStamp input state.");

            Command appleStampCommand = transaction.getCommands(AppleStampContract.AppleCommands.class).get(0);
            requireThat(appleStampCommand.getClass() == AppleStampContract.AppleCommands.Redeem.class,
                    "This transaction should include one AppleStamp input state.");

            /* Check the content of output state*/
            AppleStamp inputAppleStamp = transaction.getInputStates(AppleStamp.class).get(0);
            BasketOfApples inputBasketOfApples = transaction.getInputStates(BasketOfApples.class).get(0);

            requireThat(inputAppleStamp.getIssuer() == inputBasketOfApples.getFarm(),
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
=======
import java.util.List;

public class BasketOfApplesContract implements Contract {

    @Override
    public void verify(UtxoLedgerTransaction transaction) {
        // Extract the command from the transaction
        final Command command = transaction.getCommands().get(0);

        if (command instanceof AppleCommands.PackBasket) {
            //Retrieve the output state of the transaction
            BasketOfApples output = transaction.getOutputStates(BasketOfApples.class).get(0);
            require(
                    transaction.getOutputContractStates().size() == 1,
                    "This transaction should only output one BasketOfApples state"
            );
            require(
                    !output.getDescription().isBlank(),
                    "The output BasketOfApples state should have clear description of Apple product"
            );
            require(
                    output.getWeight() > 0,
                    "The output BasketOfApples state should have non zero weight"
            );
        } else if (command instanceof AppleCommands.Redeem) {
            require(
                    transaction.getInputContractStates().size() == 2,
                    "This transaction should consume two states"
            );

            // Retrieve the inputs to this transaction, which should be exactly one AppleStamp
            // and one BasketOfApples
            List<AppleStamp> stampInputs = transaction.getInputStates(AppleStamp.class);
            List<BasketOfApples> basketInputs = transaction.getInputStates(BasketOfApples.class);

            require(
                    !stampInputs.isEmpty() && !basketInputs.isEmpty(),
                    "This transaction should have exactly one AppleStamp and one BasketOfApples input state"
            );
            require(
                    stampInputs.get(0).getIssuer().equals(basketInputs.get(0).getFarm()),
                    "The issuer of the Apple stamp should be the producing farm of this basket of apple"
            );
            require(
                    basketInputs.get(0).getWeight() > 0,
                    "The basket of apple has to weigh more than 0"
            );
        } else {
            throw new IllegalArgumentException(String.format("Incorrect type of BasketOfApples commands: %s", command.getClass().toString()));
        }
    }

    private void require(boolean asserted, String errorMessage) {
        if (!asserted) {
            throw new CordaRuntimeException(errorMessage);
        }
    }
}
>>>>>>> main
