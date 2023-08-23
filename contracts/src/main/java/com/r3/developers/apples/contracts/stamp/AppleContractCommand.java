package com.r3.developers.apples.contracts.stamp;

import net.corda.v5.base.exceptions.CordaRuntimeException;

public abstract class AppleContractCommand implements VerifiableAppleStampCommand{
    AppleContractCommand() { }

    public void requireThat(boolean asserted, String errorMessage) {
        if(!asserted) {
            throw new CordaRuntimeException("Failed requirement: " + errorMessage);
        }
    }
}
