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
}