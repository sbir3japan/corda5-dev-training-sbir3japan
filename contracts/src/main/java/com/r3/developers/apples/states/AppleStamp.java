package com.r3.developers.apples.states;

import com.r3.developers.apples.contracts.applestamp.AppleStampContract;
import net.corda.v5.base.annotations.ConstructorForDeserialization;
import net.corda.v5.ledger.utxo.BelongsToContract;
import net.corda.v5.ledger.utxo.ContractState;
import java.security.PublicKey;
import java.util.*;

/**
 * りんごの引換券を表すState
 * id: 引換券ID
 * stampDesc: 引換券の説明
 * issuer: 引換券の発行者
 * holder: 引換券を持つ人
 */
@BelongsToContract(AppleStampContract.class)
public class AppleStamp implements ContractState {
    private UUID id;
    private String stampDesc;
    private PublicKey issuer;
    private PublicKey holder;

    @ConstructorForDeserialization
    public AppleStamp(UUID id, String stampDesc, PublicKey issuer, PublicKey holder) {
        this.id = id;
        this.stampDesc = stampDesc;
        this.issuer = issuer;
        this.holder = holder;
    }

    public UUID getId() {
        return this.id;
    }

    public String getStampDesc() {
        return this.stampDesc;
    }

    public PublicKey getIssuer() {
        return this.issuer;
    }

    public PublicKey getHolder() {
        return this.holder;
    }

    @Override
    public List<PublicKey> getParticipants() {
        return new ArrayList<>(Arrays.asList(this.issuer, this.holder));
    }

}