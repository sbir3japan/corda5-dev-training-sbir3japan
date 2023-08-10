package com.r3.developers.apples.states;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.security.PublicKey;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AppleStampTest {

    /**
     * Task 1.
     * TODO: Add an 'id' property of type {@link UUID} to the {@link AppleStamp} class to get this test to pass.
     */
    @Test
    public void hasAppleStampIdFieldOfCorrectType() throws NoSuchFieldException {
        // Does the id field exist?
        Field idField = AppleStamp.class.getDeclaredField("id");
        // Is the id field of the correct type?
        assertTrue(idField.getType().isAssignableFrom(UUID.class));
    }

    /**
     * Task 2.
     * TODO: Add an 'stampDesc' property of type {@link String} to the {@link AppleStamp} class to get this test to pass.
     */
    @Test
    public void hasAppleStampStampDescFieldOfCorrectType() throws NoSuchFieldException {
        // Does the stampDesc field exist?
        Field stampDescField = AppleStamp.class.getDeclaredField("stampDesc");
        // Is the stampDesc field of the correct type?
        assertTrue(stampDescField.getType().isAssignableFrom(String.class));
    }

    /**
     * Task 3.
     * TODO: Add an 'issuer' property of type {@link PublicKey} to the {@link AppleStamp} class to get this test to pass.
     */
    @Test
    public void hasAppleStampIssuerFieldOfCorrectType() throws NoSuchFieldException {
        // Does the issuer field exist?
        Field issuerField = AppleStamp.class.getDeclaredField("issuer");
        // Is the issuer field of the correct type?
        assertTrue(issuerField.getType().isAssignableFrom(PublicKey.class));
    }

    /**
     * Task 4.
     * TODO: Add an 'holder' property of type {@link PublicKey} to the {@link AppleStamp} class to get this test to pass.
     */
    @Test
    public void hasAppleStampHolderFieldOfCorrectType() throws NoSuchFieldException {
        // Does the holder field exist?
        Field holderField = AppleStamp.class.getDeclaredField("holder");
        // Is the holder field of the correct type?
        assertTrue(holderField.getType().isAssignableFrom(PublicKey.class));
    }

    @Test
    void getParticipants() {
    }
}