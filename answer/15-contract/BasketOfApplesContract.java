package com.r3.developers.apples.contracts.basketofapples;

import com.r3.developers.apples.states.AppleStamp;
import com.r3.developers.apples.states.BasketOfApples;
import net.corda.v5.base.exceptions.CordaRuntimeException;
import net.corda.v5.ledger.utxo.Command;
import net.corda.v5.ledger.utxo.Contract;
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;

import java.util.List;

public class BasketOfApplesContract implements Contract {

    public interface BasketOfApplesContractCommands extends Command { }

    public static class PackBasket implements BasketOfApplesContractCommands { }
    public static class Move implements BasketOfApplesContractCommands { }

    @Override
    public void verify(UtxoLedgerTransaction transaction) {

        List<? extends BasketOfApplesContractCommands> commands = transaction
                .getCommands(BasketOfApplesContractCommands.class);

        for (BasketOfApplesContractCommands command : commands) {
            if (command instanceof PackBasket) verifyPackBasket(transaction);
            else if (command instanceof Move) verifyMove(transaction);
            else throw new IllegalStateException("Unrecognised command type.");
        }
    }

    private void verifyPackBasket(UtxoLedgerTransaction transaction) {

        // 実装1-①② PackBasketのTxに関するStateの個数制約
        requireThat(transaction.getInputStates(BasketOfApples.class).size() == 0, "This transaction should not include an BasketOfApples input state.");
        requireThat(transaction.getOutputStates(BasketOfApples.class).size() == 1, "This transaction should include one BasketOfApples output state.");
//        ① "This transaction should not include an BasketOfApples input state."
//        ② "This transaction should include one BasketOfApples output state."

        // 実装1-③④ PackBasketのStateの内容に関する制約
        BasketOfApples output = transaction.getOutputStates(BasketOfApples.class).get(0);
        requireThat(!(output.getDescription().isBlank()), "The output BasketOfApples state should have clear description of Apple product.");
        requireThat(output.getWeight() > 0,"The output BasketOfApples state should have non zero weight");
//        ③ "The output BasketOfApples state should have clear description of Apple product."
//        ④ "The output BasketOfApples state should have not zero weight."

    }

    private void verifyMove(UtxoLedgerTransaction transaction) {

        // 実装2-①② MoveのTxに関する BasketOfApples の State 個数制約
//        ① "This transaction should include one BasketOfApples input state."
//        ② "This transaction should include one BasketOfApples output state."
        requireThat(transaction.getInputStates(BasketOfApples.class).size() == 1,
                "This transaction should include one BasketOfApples input state.");
        requireThat(transaction.getOutputStates(BasketOfApples.class).size() == 1,
                "This transaction should include one BasketOfApples output state.");

        // 実装2-③ MoveのTxに関する AppleStamp の State 個数制約
//        ③ "This transaction should include one AppleStamp input state."
        requireThat(transaction.getInputStates(AppleStamp.class).size() == 1,
                "This transaction should include one AppleStamp input state.");

        // 実装2-④⑤ MoveのStateの内容に関する制約
        AppleStamp inputAppleStamp = transaction.getInputStates(AppleStamp.class).get(0);
        BasketOfApples inputBasketOfApples = transaction.getInputStates(BasketOfApples.class).get(0);
        BasketOfApples outputBasketOfApples = transaction.getOutputStates(BasketOfApples.class).get(0);
//        ④ "The issuer of the Apple stamp should be the producing farm of this basket of apple."
//        ⑤ "The owner of the input and output should be changed."
        requireThat(inputAppleStamp.getIssuer().equals(inputBasketOfApples.getFarmer()),
                "The issuer of the Apple stamp should be the producing farm of this basket of apple.");
        requireThat(!(inputBasketOfApples.getOwner().equals(outputBasketOfApples.getOwner())),
                "The owner of the input and output should be changed.");

    }

    public void requireThat(boolean asserted, String errorMessage) {
        if(!asserted) {
            throw new CordaRuntimeException("Failed requirement: " + errorMessage);
        }
    }

}
