package com.r3.developers.apples.states;

import com.r3.developers.apples.contracts.BasketOfApplesContract;
import net.corda.v5.base.annotations.ConstructorForDeserialization;
import net.corda.v5.ledger.utxo.BelongsToContract;
import net.corda.v5.ledger.utxo.ContractState;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * BasketOfApples:
 * 箱詰めされたリンゴを表すState。
 */
@BelongsToContract(BasketOfApplesContract.class)
public class BasketOfApples implements ContractState {

    @ConstructorForDeserialization
    public BasketOfApples(String description,
                          PublicKey farm,
                          PublicKey owner,
                          Integer weight) {
    }
    @NotNull
    @Override
    public List<PublicKey> getParticipants() {
        return Collections.emptyList();
    }
}