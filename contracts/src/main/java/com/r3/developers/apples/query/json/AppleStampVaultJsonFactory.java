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

    static class AppleStampJson {

        private final String id;
        private final String stampDesc;
        private final String issuer;
        private final String holder;

        public AppleStampJson(String id, String stampDesc, String issuer, String holder) {
            this.id = id;
            this.stampDesc = stampDesc;
            this.issuer = issuer;
            this.holder = holder;
        }

        @NotNull
        public String getId() {
            return id;
        }

        @NotNull
        public String getStampDesc() {
            return stampDesc;
        }

        @NotNull
        public String getIssuer() {
            return issuer;
        }

        @NotNull
        public String getHolder() {
            return holder;
        }

    }


}
