package com.r3.developers.apples.contracts.stamp;

import net.corda.v5.base.exceptions.CordaRuntimeException;

public abstract class AppleContractCommand implements VerifiableAppleStampCommand{

    /* AppleContractCommandのコンストラクタ。publicを付与していないため、アクセスできるのは同一packageのclassのみ。
    * package外で本抽象クラスを無断で継承して不正なコマンドを作ることを防止している。 */
    AppleContractCommand() { }

    public void requireThat(boolean asserted, String errorMessage) {
        if(!asserted) {
            throw new CordaRuntimeException("Failed requirement: " + errorMessage);
        }
    }
}
