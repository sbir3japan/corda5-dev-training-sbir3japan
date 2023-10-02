package com.r3.developers.apples.query.json;

import com.r3.developers.apples.states.AppleStamp;
import net.corda.v5.application.marshalling.JsonMarshallingService;
import net.corda.v5.ledger.utxo.query.json.ContractStateVaultJsonFactory;
import org.jetbrains.annotations.NotNull;

public class AppleStampVaultJsonFactory implements ContractStateVaultJsonFactory<AppleStamp> {
    @NotNull
    @Override
    public Class<AppleStamp> getStateType() {
        return AppleStamp.class;
    }

    @NotNull
    @Override
    public String create(@NotNull AppleStamp state, @NotNull JsonMarshallingService jsonMarshallingService) {
        return jsonMarshallingService.format(new AppleStampJson(
                state.getId().toString(),
                state.getStampDesc(),
                state.getIssuer().toString(),
                state.getHolder().toString()
        ));
    }
}