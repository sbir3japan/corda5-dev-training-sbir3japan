package com.r3.developers.apples.contracts.applestamp;

import net.corda.v5.base.exceptions.CordaRuntimeException;

public abstract class AppleStampContractCommand implements VerifiableAppleCommand {

    // AppleStampContractCommand のコンストラクタです.
    // publicを付与していないため、アクセスできるのは同一packageのclassのみです.
    // package外で本抽象クラスを無断で継承して不正なコマンドを作ることを防止しています.
    AppleStampContractCommand() { }

    public void requireThat(boolean asserted, String errorMessage) {
        if(!asserted) {
            throw new CordaRuntimeException("Failed requirement: " + errorMessage);
        }
    }
}
