package com.r3.developers.apples.states;

import com.r3.developers.apples.contracts.BasketOfApplesContract;
import net.corda.v5.base.annotations.ConstructorForDeserialization;
import net.corda.v5.ledger.utxo.BelongsToContract;
import net.corda.v5.ledger.utxo.ContractState;
import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * BasketOfApples:
 * 箱詰めされたリンゴを表すState。
 */
@BelongsToContract(BasketOfApplesContract.class)
public class BasketOfApples implements ContractState {

    private final String description;
    private final PublicKey farm;
    private final PublicKey owner;
    private final Integer weight;
    private final List<PublicKey> participants;

    @ConstructorForDeserialization
    public BasketOfApples(String description,
                          PublicKey farm,
                          PublicKey owner,
                          Integer weight) {
        this.description = description;
        this.farm = farm;
        this.owner = owner;
        this.weight = weight;
        this.participants = new ArrayList<>(Arrays.asList(farm, owner));
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
    @NotNull
    @Override
    public List<PublicKey> getParticipants() {
        return participants;
    }

    public BasketOfApples changeOwner(PublicKey buyer) {
        return new BasketOfApples(description, farm, buyer, weight);
    }
}