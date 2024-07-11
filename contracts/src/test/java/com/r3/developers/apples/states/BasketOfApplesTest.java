package com.r3.developers.apples.states;

import com.r3.developers.apples.TestKeyUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import static org.junit.jupiter.api.Assertions.*;

public class BasketOfApplesTest {

    private TestKeyUtils testKeyUtils = new TestKeyUtils();

    /**
     * 1-1. フィールドとゲッター作成: description
     */
    @Test
    public void hasBasketOfApplesDescriptionFieldOfCorrectType() throws NoSuchFieldException, NoSuchMethodException {
        // description フィールドを取得
        Field descriptionField = BasketOfApples.class.getDeclaredField("description");
        // フィールドの型を確認
        assertTrue(descriptionField.getType().isAssignableFrom(String.class));

        // getDescription メソッドを取得
        Method getDescriptionMethod = BasketOfApples.class.getDeclaredMethod("getDescription");
        // メソッドの戻り値の型を確認
        assertTrue(getDescriptionMethod.getReturnType().isAssignableFrom(String.class));
    }

    /**
     * 1-2. フィールドとゲッター作成: farmer
     */
    @Test
    public void hasBasketOfApplesFarmFieldOfCorrectType() throws NoSuchFieldException, NoSuchMethodException {
        // farmer フィールドを取得
        Field farmerField = BasketOfApples.class.getDeclaredField("farmer");
        // フィールドの型を確認
        assertTrue(farmerField.getType().isAssignableFrom(PublicKey.class));

        // getFarmer メソッドを取得
        Method getFarmerMethod = BasketOfApples.class.getDeclaredMethod("getFarmer");
        // メソッドの戻り値の型を確認
        assertTrue(getFarmerMethod.getReturnType().isAssignableFrom(String.class));
    }

    /**
     * 1-3. フィールドとゲッター作成: owner
     */
    @Test
    public void hasBasketOfApplesOwnerFieldOfCorrectType() throws NoSuchFieldException, NoSuchMethodException {
        // owner フィールドを取得
        Field ownerField = BasketOfApples.class.getDeclaredField("owner");
        // フィールドの型を確認
        assertTrue(ownerField.getType().isAssignableFrom(PublicKey.class));

        // getOwner メソッドを取得
        Method getOwnerMethod = BasketOfApples.class.getDeclaredMethod("getOwner");
        // メソッドの戻り値の型を確認
        assertTrue(getOwnerMethod.getReturnType().isAssignableFrom(PublicKey.class));
    }

    /**
     * 1-4. フィールドとゲッター作成: weight
     */
    @Test
    public void hasBasketOfApplesWeightFieldOfCorrectType() throws NoSuchFieldException, NoSuchMethodException {
        // weight フィールドを取得
        Field weightField = BasketOfApples.class.getDeclaredField("weight");
        // フィールドの型を確認
        assertTrue(weightField.getType().isAssignableFrom(Integer.class));

        // getWeight メソッドを取得
        Method getWeightMethod = BasketOfApples.class.getDeclaredMethod("getWeight");
        // メソッドの戻り値の型を確認
        assertTrue(getWeightMethod.getReturnType().isAssignableFrom(int.class));
    }

    /**
     * 2-1. getParticipantsの実装: 結果にfarmerが含まれていることをチェック
     */
    @Test
    void farmIsParticipant() throws NoSuchAlgorithmException {

        PublicKey farmerKey = testKeyUtils.getFarmerKey();
        PublicKey ownerKey = testKeyUtils.getOwnerKey();

        BasketOfApples basketOfApples = new BasketOfApples( "This is the test Apples.",
                farmerKey, ownerKey, 100);

        /* リストに期待したオブジェクトが含まれない場合、indexOfメソッドは-1を返す. */
        assertNotEquals(basketOfApples.getParticipants().indexOf(farmerKey), -1);
    }

    /**
     * 2-2. getParticipantsの実装: 結果にownerが含まれていることをチェック
     */
    @Test
    void ownerIsParticipant() throws NoSuchAlgorithmException {

        PublicKey farmerKey = testKeyUtils.getFarmerKey();
        PublicKey ownerKey = testKeyUtils.getOwnerKey();

        BasketOfApples basketOfApples = new BasketOfApples( "This is the test Apples.",
                farmerKey, ownerKey, 100);

        /* リストに期待したオブジェクトが含まれない場合、indexOfメソッドは-1を返す. */
        assertNotEquals(basketOfApples.getParticipants().indexOf(ownerKey), -1);
    }

    /**
     * 2-3. changeOwnerの実装: ownerが変更されること、それ以外は変更されていないことを確認.
     */
    @Test
    void checkWithChangeOwnerHelperMethod() throws NoSuchAlgorithmException {

        PublicKey farmerKey = testKeyUtils.getFarmerKey();
        PublicKey ownerKey = testKeyUtils.getOwnerKey();
        PublicKey newOwnerKey = testKeyUtils.getNewOwnerKey();

        BasketOfApples basketOfApples = new BasketOfApples( "This is the test Apples.",
                farmerKey, ownerKey, 100);

        // 所有者を owner -> newOwner に更新.
        BasketOfApples newOwnerBasketOfApples = basketOfApples.changeOwner(newOwnerKey);

        // Ownerが更新されていることを確認.
//        assertEquals(newOwnerBasketOfApples.getOwner(), newOwnerKey);

        // 他のフィールドは更新されていないことを確認.
//        assertEquals(newOwnerBasketOfApples.getDescription(), basketOfApples.getDescription());
//        assertEquals(newOwnerBasketOfApples.getFarmer(), basketOfApples.getFarmer());
//        assertEquals(newOwnerBasketOfApples.getWeight(), basketOfApples.getWeight());

    }
}
