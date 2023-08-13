package com.r3.developers.apples.states;

import com.r3.developers.apples.contracts.AppleStampContract;
import net.corda.v5.base.annotations.ConstructorForDeserialization;
import net.corda.v5.ledger.utxo.BelongsToContract;
import net.corda.v5.ledger.utxo.ContractState;
<<<<<<< HEAD

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@BelongsToContract(AppleStampContract.class)
public class AppleStamp implements ContractState {

=======
import java.security.PublicKey;
import java.util.*;

@BelongsToContract(AppleStampContract.class)
public class AppleStamp implements ContractState {
>>>>>>> main
    private UUID id;
    private String stampDesc;
    private PublicKey issuer;
    private PublicKey holder;
<<<<<<< HEAD
    public List<PublicKey> participants;

    // Allows serialisation and to use a specified UUID.
    @ConstructorForDeserialization
    public AppleStamp(UUID id,
                      String stampDesc,
                      PublicKey issuer,
                      PublicKey holder) {
=======
    private List<PublicKey> participants;

    @ConstructorForDeserialization
    public AppleStamp(UUID id, String stampDesc, PublicKey issuer, PublicKey holder, List<PublicKey> participants) {
>>>>>>> main
        this.id = id;
        this.stampDesc = stampDesc;
        this.issuer = issuer;
        this.holder = holder;
<<<<<<< HEAD
        this.participants = new ArrayList(Arrays.asList(issuer, holder));
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
=======
        this.participants = participants;
>>>>>>> main
    }

    @Override
    public List<PublicKey> getParticipants() {
        return participants;
    }

<<<<<<< HEAD
=======
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
>>>>>>> main
}