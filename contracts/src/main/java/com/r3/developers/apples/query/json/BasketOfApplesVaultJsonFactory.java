package com.r3.developers.apples.query.json;

import com.r3.developers.apples.states.BasketOfApples;
import net.corda.v5.application.marshalling.JsonMarshallingService;
import net.corda.v5.ledger.utxo.query.json.ContractStateVaultJsonFactory;
import org.jetbrains.annotations.NotNull;

public class BasketOfApplesVaultJsonFactory implements ContractStateVaultJsonFactory<BasketOfApples> {
    @NotNull
    @Override
    public Class<BasketOfApples> getStateType() {
        return BasketOfApples.class;
    }

    @NotNull
    @Override
    public String create(@NotNull BasketOfApples state, @NotNull JsonMarshallingService jsonMarshallingService) {
        return jsonMarshallingService.format(new BasketOfApplesJson(
                state.getDescription(),
                state.getFarm().toString(),
                state.getOwner().toString(),
                state.getWeight()
        ));
    }

    static class BasketOfApplesJson {

        private final String description;
        private final String farm;
        private final String owner;
        private final Integer weight;

        public BasketOfApplesJson(String description, String farm, String owner, Integer weight) {
            this.description = description;
            this.farm = farm;
            this.owner = owner;
            this.weight = weight;
        }

        @NotNull
        public String getDescription() {
            return description;
        }

        @NotNull
        public String getFarm() {
            return farm;
        }

        @NotNull
        public String getOwner() {
            return owner;
        }

        @NotNull
        public Integer getWeight () {
            return weight;
        }

    }


}
