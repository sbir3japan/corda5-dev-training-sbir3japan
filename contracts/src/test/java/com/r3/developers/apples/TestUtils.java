package com.r3.developers.apples;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class TestUtils {

    /** TODO:
     * キーペアを作成。ダミーNodeを生成するテストフレームワークの用意がないため、KeyPairGeneratorで代替。
     * 将来のテストフレームワークで上記機能が提供された場合は変更する。
    */
    public static KeyPair generateKey() throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(4096);
        return keyPairGenerator.generateKeyPair();
    }
}
