package com.r3.developers.apples.states;

import com.r3.developers.apples.contracts.stamp.AppleStampContract;
import net.corda.v5.base.annotations.ConstructorForDeserialization;
import net.corda.v5.base.types.MemberX500Name;
import net.corda.v5.ledger.utxo.BelongsToContract;
import net.corda.v5.ledger.utxo.ContractState;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.*;

/**
 * AppleStamp:
 * リンゴ引換券を表すState。リンゴの生産者が発行する。
 */
@BelongsToContract(AppleStampContract.class)
public class AppleStamp implements ContractState {

    @ConstructorForDeserialization
    public AppleStamp(UUID id,
                      String stampDesc,
                      PublicKey issuer,
                      PublicKey holder) {
    }

    @NotNull
    @Override
    public List<PublicKey> getParticipants() {
        return Collections.emptyList();
    }
}