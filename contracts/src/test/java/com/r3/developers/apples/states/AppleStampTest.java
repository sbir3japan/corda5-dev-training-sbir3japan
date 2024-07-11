package com.r3.developers.apples.states;

import com.r3.developers.apples.TestKeyUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AppleStampTest {

    private TestKeyUtils testKeyUtils = new TestKeyUtils();

    /**
     * AppleStamp test 1.
     * AppleStampに 'id' というフィールドが存在することをチェックします.
     */
    @Test
    public void hasAppleStampIdFieldOfCorrectType() throws NoSuchFieldException {
        // id フィールドを取得
        Field idField = AppleStamp.class.getDeclaredField("id");
        // フィールドの型を確認
        assertTrue(idField.getType().isAssignableFrom(UUID.class));
    }

    /**
     * AppleStamp test 2.
     * AppleStampに 'stampDesc' というフィールドが存在することをチェックします.
     */
    @Test
    public void hasAppleStampStampDescFieldOfCorrectType() throws NoSuchFieldException {
        // stampDesc フィールドを取得
        Field stampDescField = AppleStamp.class.getDeclaredField("stampDesc");
        // フィールドの型を確認
        assertTrue(stampDescField.getType().isAssignableFrom(String.class));
    }

    /**
     * AppleStamp test 3.
     * AppleStampに 'issuer' というフィールドが存在することをチェックします.
     */
    @Test
    public void hasAppleStampIssuerFieldOfCorrectType() throws NoSuchFieldException {
        // issuer フィールドを取得
        Field issuerField = AppleStamp.class.getDeclaredField("issuer");
        // フィールドの型を確認
        assertTrue(issuerField.getType().isAssignableFrom(PublicKey.class));
    }

    /**
     * AppleStamp test 4.
     * AppleStampに 'holder' というフィールドが存在することをチェックします.
     */
    @Test
    public void hasAppleStampHolderFieldOfCorrectType() throws NoSuchFieldException {
        // holder フィールドを取得
        Field holderField = AppleStamp.class.getDeclaredField("holder");
        // フィールドの型を確認
        assertTrue(holderField.getType().isAssignableFrom(PublicKey.class));
    }

    /**
     * AppleStamp test 5.
     * getParticipants() を実行した際、結果にissuerが含まれていることをチェックします.
     */
    @Test
    void issuerIsParticipant() throws NoSuchAlgorithmException {

        PublicKey issuerKeys = testKeyUtils.getIssuerKey();
        PublicKey holderKeys = testKeyUtils.getHolderKey();

        AppleStamp appleStamp = new AppleStamp(
                UUID.randomUUID(),
                "This is a test stamp.",
                issuerKeys,
                holderKeys
        );

        // リストに期待したオブジェクトが含まれない場合、indexOfメソッドは-1を返します.
        assertNotEquals(appleStamp.getParticipants().indexOf(issuerKeys), -1);
    }

    /**
     * AppleStamp test 6.
     * getParticipants() を実行した際、結果にissuerが含まれていることをチェックします.
     */
    @Test
    void holderIsParticipant() throws NoSuchAlgorithmException {

        PublicKey issuerKeys = testKeyUtils.getIssuerKey();
        PublicKey holderKeys = testKeyUtils.getHolderKey();

        AppleStamp appleStamp = new AppleStamp(
                UUID.randomUUID(),
                "This is a test stamp.",
                issuerKeys,
                holderKeys
        );

        // リストに期待したオブジェクトが含まれない場合、indexOfメソッドは-1を返します.
        assertNotEquals(appleStamp.getParticipants().indexOf(holderKeys), -1);
    }
}
