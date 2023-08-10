package com.r3.developers.apples.states;

import com.r3.developers.apples.contracts.BasketOfApplesContract;
import net.corda.v5.base.annotations.ConstructorForDeserialization;
import net.corda.v5.ledger.utxo.BelongsToContract;
import net.corda.v5.ledger.utxo.ContractState;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@BelongsToContract(BasketOfApplesContract.class)
public class BasketOfApples implements ContractState {

    private String description;
    private PublicKey farm;
    private PublicKey owner;

    private Integer weight;
    public List<PublicKey> participants;

    // Allows serialisation and to use a specified UUID.
    @ConstructorForDeserialization
    public BasketOfApples(String description,
                          PublicKey farm,
                          PublicKey owner,
                          Integer weight) {
        this.description = description;
        this.farm = farm;
        this.owner = owner;
        this.weight = weight;
        this.participants = new ArrayList(Arrays.asList(farm, owner));
    }

    public String getDescription() {
        return description;
    }
    
    public PublicKey getFarm() {
        return farm;
    }

    public PublicKey getOwner() {
        return owner;
    }

    public Integer getWeight() {
        return weight;
    }
    @Override
    public List<PublicKey> getParticipants() {
        return participants;
    }

    public BasketOfApples changeOwner(PublicKey buyer) {
        return new BasketOfApples(description, farm, buyer, weight);
    }

}