package com.r3.developers.apples.states;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.security.PublicKey;

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

}