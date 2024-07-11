package com.r3.developers.apples.contracts.applestamp;

import net.corda.v5.ledger.utxo.Command;
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;

public interface VerifiableAppleCommand extends Command {
    void verify(UtxoLedgerTransaction transaction);
    void requireThat(boolean asserted, String errorMessage);
}
