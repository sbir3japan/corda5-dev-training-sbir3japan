package com.r3.developers.apples.states;

import com.r3.developers.apples.TestUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayList;

class BasketOfApplesTest {

    /**
     * Task 1.
     * TODO: Add an 'description' property of type {@link String} to the {@link BasketOfApples} class to get this test to pass.
     */
    @Test
    public void hasBasketOfApplesDescriptionFieldOfCorrectType() throws NoSuchFieldException {
        // Does the description field exist?
        Field descriptionField = BasketOfApples.class.getDeclaredField("description");
        // Is the description field of the correct type?
        assertTrue(descriptionField.getType().isAssignableFrom(String.class));
    }

    /**
     * Task 2.
     * TODO: Add an 'farm' property of type {@link PublicKey} to the {@link BasketOfApples} class to get this test to pass.
     */
    @Test
    public void hasBasketOfApplesFarmFieldOfCorrectType() throws NoSuchFieldException {
        // Does the farm field exist?
        Field farmField = BasketOfApples.class.getDeclaredField("farm");
        // Is the farm field of the correct type?
        assertTrue(farmField.getType().isAssignableFrom(PublicKey.class));
    }

    /**
     * Task 3.
     * TODO: Add an 'owner' property of type {@link PublicKey} to the {@link BasketOfApples} class to get this test to pass.
     */
    @Test
    public void hasBasketOfApplesOwnerFieldOfCorrectType() throws NoSuchFieldException {
        // Does the owner field exist?
        Field ownerField = BasketOfApples.class.getDeclaredField("owner");
        // Is the owner field of the correct type?
        assertTrue(ownerField.getType().isAssignableFrom(PublicKey.class));
    }

    /**
     * Task 4.
     * TODO: Add an 'weight' property of type {@link Integer} to the {@link BasketOfApples} class to get this test to pass.
     */
    @Test
    public void hasBasketOfApplesWeightFieldOfCorrectType() throws NoSuchFieldException {
        // Does the weight field exist?
        Field weightField = BasketOfApples.class.getDeclaredField("weight");
        // Is the weight field of the correct type?
        assertTrue(weightField.getType().isAssignableFrom(Integer.class));
    }

    /**
     * Task 5.
     * TODO: Check to see if the BasketOfApples's farm field is included in the participants.
     */
    @Test
    void farmIsParticipant() throws NoSuchAlgorithmException {

        ArrayList<PublicKey> publicKeys = new ArrayList<>();

        KeyPair farmKeys = TestUtils.generateKey();
        publicKeys.add(farmKeys.getPublic());

        KeyPair ownerKeys = TestUtils.generateKey();
        publicKeys.add(ownerKeys.getPublic());

        BasketOfApples basketOfApples = new BasketOfApples( "Fuji, Aomori.",
                farmKeys.getPublic(), ownerKeys.getPublic(), 100);

        /* リストに期待したオブジェクトが含まれない場合、indexOfメソッドは-1を返す. */
        assertNotEquals(basketOfApples.getParticipants().indexOf(farmKeys.getPublic()), -1);
    }

    /**
     * Task 6.
     * TODO: Check to see if the AppleState's owner field is included in the participants.
     */
    @Test
    void ownerIsParticipant() throws NoSuchAlgorithmException {

        ArrayList<PublicKey> publicKeys = new ArrayList<>();

        KeyPair farmKeys = TestUtils.generateKey();
        publicKeys.add(farmKeys.getPublic());

        KeyPair ownerKeys = TestUtils.generateKey();
        publicKeys.add(ownerKeys.getPublic());

        BasketOfApples basketOfApples = new BasketOfApples( "Fuji, Aomori.",
                farmKeys.getPublic(), ownerKeys.getPublic(), 100);

        /* リストに期待したオブジェクトが含まれない場合、indexOfメソッドは-1を返す. */
        assertNotEquals(basketOfApples.getParticipants().indexOf(ownerKeys.getPublic()), -1);
    }

    /**
     * Task 7.
     * TODO: Verified that the helper method to update Owner functions correctly.
     */
    @Test
    void checkWithChangeOwnerHelperMethod() throws NoSuchAlgorithmException {

        ArrayList<PublicKey> publicKeys = new ArrayList<>();

        KeyPair farmKeys = TestUtils.generateKey();
        publicKeys.add(farmKeys.getPublic());

        KeyPair ownerKeys = TestUtils.generateKey();
        publicKeys.add(ownerKeys.getPublic());

        KeyPair newOwnerKeys = TestUtils.generateKey();
        publicKeys.add(newOwnerKeys.getPublic());

        BasketOfApples basketOfApples = new BasketOfApples( "Fuji, Aomori.",
                farmKeys.getPublic(), ownerKeys.getPublic(), 100);

        /* 鍵更新*/
        BasketOfApples newOwnerBasketOfApples = basketOfApples.changeOwner(newOwnerKeys.getPublic());

        /* Ownerの鍵が更新されていることを確認。 */
        assertEquals(newOwnerBasketOfApples.getOwner(), newOwnerKeys.getPublic());

        /* 他のフィールドが更新されていないことを確認。 */
        assertEquals(newOwnerBasketOfApples.getDescription(), basketOfApples.getDescription());
        assertEquals(newOwnerBasketOfApples.getFarm(), basketOfApples.getFarm());
        assertEquals(newOwnerBasketOfApples.getWeight(), basketOfApples.getWeight());

    }
}