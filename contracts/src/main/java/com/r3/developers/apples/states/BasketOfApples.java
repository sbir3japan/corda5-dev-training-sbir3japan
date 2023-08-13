package com.r3.developers.apples.states;

import com.r3.developers.apples.contracts.BasketOfApplesContract;
import net.corda.v5.base.annotations.ConstructorForDeserialization;
import net.corda.v5.ledger.utxo.BelongsToContract;
import net.corda.v5.ledger.utxo.ContractState;
<<<<<<< HEAD

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
=======
import java.security.PublicKey;
>>>>>>> main
import java.util.List;

@BelongsToContract(BasketOfApplesContract.class)
public class BasketOfApples implements ContractState {
<<<<<<< HEAD

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
=======
    private String description;
    private PublicKey farm;
    private PublicKey owner;
    private int weight;
    private List<PublicKey> participants;

    @ConstructorForDeserialization
    public BasketOfApples(
            String description, PublicKey farm, PublicKey owner, int weight, List<PublicKey> participants
    ) {
>>>>>>> main
        this.description = description;
        this.farm = farm;
        this.owner = owner;
        this.weight = weight;
<<<<<<< HEAD
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
=======
        this.participants = participants;
    }

>>>>>>> main
    public List<PublicKey> getParticipants() {
        return participants;
    }

<<<<<<< HEAD
    public BasketOfApples changeOwner(PublicKey buyer) {
        return new BasketOfApples(description, farm, buyer, weight);
    }

=======
    public String getDescription() {
        return this.description;
    }

    public PublicKey getFarm() {
        return this.farm;
    }

    public PublicKey getOwner() {
        return this.owner;
    }

    public int getWeight() {
        return this.weight;
    }

    public BasketOfApples changeOwner(PublicKey buyer) {
        List<PublicKey> participants = List.of(farm, buyer);
        return new BasketOfApples(this.description, this.farm, buyer, this.weight, participants);
    }
>>>>>>> main
}