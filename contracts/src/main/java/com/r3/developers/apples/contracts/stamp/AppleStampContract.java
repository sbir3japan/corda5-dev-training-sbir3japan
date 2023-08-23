package com.r3.developers.apples.contracts.stamp;

import net.corda.v5.ledger.utxo.Command;
import net.corda.v5.ledger.utxo.Contract;
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;

import java.util.List;

public class AppleStampContract implements Contract {

    public interface AppleCommands extends Command {
        class Issue extends AppleCreateCommand { }
        class Redeem extends AppleRedeemCommand { }
    }

    @Override
    public void verify(UtxoLedgerTransaction transaction) {

        List<? extends AppleContractCommand> commands = transaction
                .getCommands(AppleContractCommand.class);

        for (AppleContractCommand command : commands) {
            command.verify(transaction);
        }
    }
}