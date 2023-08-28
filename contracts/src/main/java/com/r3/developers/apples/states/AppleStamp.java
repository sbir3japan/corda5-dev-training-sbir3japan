package com.r3.developers.apples.states;

import com.r3.developers.apples.contracts.stamp.AppleStampContract;
import net.corda.v5.base.annotations.ConstructorForDeserialization;
import net.corda.v5.base.types.MemberX500Name;
import net.corda.v5.ledger.utxo.BelongsToContract;
import net.corda.v5.ledger.utxo.ContractState;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * AppleStamp:
 * リンゴ引換券を表すState。リンゴの生産者が発行する。
 */
@BelongsToContract(AppleStampContract.class)
public class AppleStamp implements ContractState {

    private final UUID id;
    private final String stampDesc;
    private final PublicKey issuer;
    private final PublicKey holder;
    private final List<PublicKey> participants;

    @ConstructorForDeserialization
    public AppleStamp(UUID id,
                      String stampDesc,
                      PublicKey issuer,
                      PublicKey holder) {
        this.id = id;
        this.stampDesc = stampDesc;
        this.issuer = issuer;
        this.holder = holder;
        this.participants = new ArrayList<>(Arrays.asList(issuer, holder));
    }

    public UUID getId() {
        return id;
    }

    public String getStampDesc() {
        return stampDesc;
    }

    public PublicKey getIssuer() {
        return issuer;
    }

    public PublicKey getHolder() {
        return holder;
    }

    @NotNull
    @Override
    public List<PublicKey> getParticipants() {
        return participants;
    }
}