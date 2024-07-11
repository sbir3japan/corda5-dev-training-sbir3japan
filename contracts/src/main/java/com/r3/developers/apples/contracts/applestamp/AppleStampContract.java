package com.r3.developers.apples.contracts.applestamp;

import net.corda.v5.base.exceptions.CordaRuntimeException;
import net.corda.v5.ledger.utxo.Command;
import net.corda.v5.ledger.utxo.Contract;
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;

import java.util.List;

public class AppleStampContract implements Contract {

    public interface AppleCommands extends Command {
        class Issue extends AppleIssueCommand { }
        class Redeem extends AppleRedeemCommand { }
    }

    @Override
    public void verify(UtxoLedgerTransaction transaction) {

        // AppleContractCommandを継承しているclassのみ取得.
        List<? extends AppleStampContractCommand> commands = transaction
                .getCommands(AppleStampContractCommand.class);

        if (commands.size() == 0) {
            throw new CordaRuntimeException("Incorrect type of AppleStamp commands: " +
                    transaction.getCommands().get(0).getClass());
        }

        for (AppleStampContractCommand command : commands) {
            command.verify(transaction);
        }
    }
}
