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

        class Move implements BasketOfApplesCommands {
        }
    }

    private void requireThat(boolean asserted, String errorMessage) {
        //実装①-1
    }

    @Override
    public void verify(UtxoLedgerTransaction transaction) {
        //実装①-2
        BasketOfApplesCommands basketOfApplesCommand;
        //実装①-3

        //実装①-4 PackBasket-個数制約
        //"This transaction should not include an BasketOfApples input state."
        //"This transaction should include one BasketOfApples output state."

        //実装①-5 PackBasket-内容制約
        //"The output BasketOfApples state should have clear description of Apple product"
        //"The output BasketOfApples state should have non zero weight"
        //

        //実装①-6 Move-個数制約
        //"This transaction should include one BasketOfApples input state."
        //"This transaction should include one BasketOfApples output state."

        //実装①-7 Move-個数制約その２
        //"This transaction should include one AppleStamp input state."

        //実装①-8 Move-内容制約
        //AppleStamp inputAppleStamp = transaction.getInputStates(AppleStamp.class).get(0);
        //BasketOfApples inputBasketOfApples = transaction.getInputStates(BasketOfApples.class).get(0);
        //BasketOfApples outputBasketOfApples = transaction.getOutputStates(BasketOfApples.class).get(0);

        //"The issuer of the Apple stamp should be the producing farm of this basket of apple"
        //"The owner of the input and output should be changed."
        throw new CordaRuntimeException("Incorrect type of BasketOfApples commands: " + transaction.getCommands().get(0).getClass());
    }
}