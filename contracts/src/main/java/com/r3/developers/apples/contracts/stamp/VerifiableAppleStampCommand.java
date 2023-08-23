package com.r3.developers.apples.contracts.stamp;

import net.corda.v5.ledger.utxo.Command;
import net.corda.v5.ledger.utxo.transaction.UtxoLedgerTransaction;

public interface VerifiableAppleStampCommand extends Command {

    void verify(UtxoLedgerTransaction transaction);

    void requireThat(boolean asserted, String errorMessage);

}
