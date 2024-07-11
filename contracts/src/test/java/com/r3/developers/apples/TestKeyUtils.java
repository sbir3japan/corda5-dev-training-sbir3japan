package com.r3.developers.apples;

import com.r3.corda.ledger.utxo.testing.ContractTest;

import java.security.PublicKey;

public class TestKeyUtils extends ContractTest {

    // Stateハンズオンで使用します
    PublicKey issuerKey = createSigningKey();
    PublicKey holderKey = createSigningKey();
    PublicKey farmerKey = createSigningKey();
    PublicKey ownerKey = createSigningKey();
    PublicKey newOwnerKey = createSigningKey();

    public PublicKey getIssuerKey() { return issuerKey; }

    public PublicKey getHolderKey() { return holderKey; }

    public PublicKey getFarmerKey() { return farmerKey; }

    public PublicKey getOwnerKey() { return ownerKey; }

    public PublicKey getNewOwnerKey() { return newOwnerKey; }

}
