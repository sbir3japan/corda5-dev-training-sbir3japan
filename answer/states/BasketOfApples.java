package com.r3.developers.apples.states;

import com.r3.developers.apples.contracts.basketofapples.BasketOfApplesContract;
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
 * かごにはいったりんごを表すState
 */
@BelongsToContract(BasketOfApplesContract.class)
public class BasketOfApples implements ContractState {
    // 実装1-①. フィールドを設定してください
    private String description;
    private PublicKey farmer;
    private PublicKey owner;
    private int weight;

    @ConstructorForDeserialization
    public BasketOfApples(
            String description, PublicKey farmer, PublicKey owner, int weight
    ) {
        // 実装2-②. コンストラクタを完成させてください
        this.description = description;
        this.farmer = farmer;
        this.owner = owner;
        this.weight = weight;
    }

    // 実装1-②. getterを追加してください
    public String getDescription() {
        return description;
    }

    public PublicKey getFarmer() {
        return farmer;
    }

    public PublicKey getOwner() {
        return owner;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    @NotNull
    public List<PublicKey> getParticipants() {
        // 実装2-①. getParticipants が関係者のリストを返すように変更してください.
        return new ArrayList<>(Arrays.asList(this.farmer, this.owner));
    }

    public BasketOfApples changeOwner(PublicKey buyer) {
        // 実装2-③. ownerが引数のbuyerになるような BasketOfApples を作成してください.
        return new BasketOfApples(this.description, this.farmer, buyer, this.weight);
    }

}