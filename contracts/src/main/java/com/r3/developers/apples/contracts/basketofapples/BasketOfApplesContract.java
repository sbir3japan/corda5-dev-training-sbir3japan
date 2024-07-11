package com.r3.developers.apples.contracts.basketofapples;

import com.r3.developers.apples.states.AppleStamp;
import com.r3.developers.apples.states.BasketOfApples;
import net.corda.v5.base.exceptions.CordaRuntimeException;
import net.corda.v5.ledger.utxo.Command;
import net.corda.v5.ledger.utxo.Contract;
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;

import java.util.List;

public class BasketOfApplesContract implements Contract {

    public interface BasketOfApplesContractCommand extends Command { }

    public static class PackBasket implements BasketOfApplesContractCommand { }
    public static class Move implements BasketOfApplesContractCommand { }

    @Override
    public void verify(UtxoLedgerTransaction transaction) {

        List<? extends BasketOfApplesContractCommand> commands = transaction
                .getCommands(BasketOfApplesContractCommand.class);

        for (BasketOfApplesContractCommand command : commands) {
            if (command instanceof PackBasket) verifyPackBasket(transaction);
            else if (command instanceof Move) verifyMove(transaction);
            else throw new IllegalStateException("Unrecognised command type.");
        }
    }

    private void verifyPackBasket(UtxoLedgerTransaction transaction) {

        // 実装1-①② PackBasketのTxに関するStateの個数制約
//        ① "This transaction should not include an BasketOfApples input state."
//        ② "This transaction should include one BasketOfApples output state."

        // 実装1-③④ PackBasketのStateの内容に関する制約
//        ③ "The output BasketOfApples state should have clear description of Apple product."
//        ④ "The output BasketOfApples state should have not zero weight."

    }

    private void verifyMove(UtxoLedgerTransaction transaction) {

        // 実装2-①② MoveのTxに関する BasketOfApples の State 個数制約
//        ① "This transaction should include one BasketOfApples input state."
//        ② "This transaction should include one BasketOfApples output state."

        // 実装2-③ MoveのTxに関する AppleStamp の State 個数制約
//        ③ "This transaction should include one AppleStamp input state."

        // 実装2-④⑤ MoveのStateの内容に関する制約
        AppleStamp inputAppleStamp = transaction.getInputStates(AppleStamp.class).get(0);
        BasketOfApples inputBasketOfApples = transaction.getInputStates(BasketOfApples.class).get(0);
        BasketOfApples outputBasketOfApples = transaction.getOutputStates(BasketOfApples.class).get(0);
//        ④ "The issuer of the Apple stamp should be the producing farm of this basket of apple."
//        ⑤ "The owner of the input and output should be changed."

    }

}
