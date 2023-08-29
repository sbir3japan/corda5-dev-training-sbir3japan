package com.r3.developers.apples.states;

import com.r3.developers.apples.TestUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AppleStampTest {

//    /**
//     * Task 1.
//     * TODO: Add an 'id' property of type {@link UUID} to the {@link AppleStamp} class to get this test to pass.
//     */
//    @Test
//    public void hasAppleStampIdFieldOfCorrectType() throws NoSuchFieldException {
//        // Does the id field exist?
//        Field idField = AppleStamp.class.getDeclaredField("id");
//        // Is the id field of the correct type?
//        assertTrue(idField.getType().isAssignableFrom(UUID.class));
//    }

//    /**
//     * Task 2.
//     * TODO: Add an 'stampDesc' property of type {@link String} to the {@link AppleStamp} class to get this test to pass.
//     */
//    @Test
//    public void hasAppleStampStampDescFieldOfCorrectType() throws NoSuchFieldException {
//        // Does the stampDesc field exist?
//        Field stampDescField = AppleStamp.class.getDeclaredField("stampDesc");
//        // Is the stampDesc field of the correct type?
//        assertTrue(stampDescField.getType().isAssignableFrom(String.class));
//    }

//    /**
//     * Task 3.
//     * TODO: Add an 'issuer' property of type {@link PublicKey} to the {@link AppleStamp} class to get this test to pass.
//     */
//    @Test
//    public void hasAppleStampIssuerFieldOfCorrectType() throws NoSuchFieldException {
//        // Does the issuer field exist?
//        Field issuerField = AppleStamp.class.getDeclaredField("issuer");
//        // Is the issuer field of the correct type?
//        assertTrue(issuerField.getType().isAssignableFrom(PublicKey.class));
//    }

//    /**
//     * Task 4.
//     * TODO: Add an 'holder' property of type {@link PublicKey} to the {@link AppleStamp} class to get this test to pass.
//     */
//    @Test
//    public void hasAppleStampHolderFieldOfCorrectType() throws NoSuchFieldException {
//        // Does the holder field exist?
//        Field holderField = AppleStamp.class.getDeclaredField("holder");
//        // Is the holder field of the correct type?
//        assertTrue(holderField.getType().isAssignableFrom(PublicKey.class));
//    }

//    /**
//     * Task 5.
//     * TODO: Check to see if the AppleState's issue field is included in the participants.
//     */
//    @Test
//    void issuerIsParticipant() throws NoSuchAlgorithmException {
//
//        ArrayList<PublicKey> publicKeys = new ArrayList<>();
//
//        KeyPair issuerKeys = TestUtils.generateKey();
//        publicKeys.add(issuerKeys.getPublic());
//
//        KeyPair holderKeys = TestUtils.generateKey();
//        publicKeys.add(holderKeys.getPublic());
//
//        AppleStamp appleStamp = new AppleStamp(UUID.randomUUID(), "This is a test stamp.",
//                issuerKeys.getPublic(), holderKeys.getPublic());
//
//        /* リストに期待したオブジェクトが含まれない場合、indexOfメソッドは-1を返す. */
//        assertNotEquals(appleStamp.getParticipants().indexOf(issuerKeys.getPublic()), -1);
//    }

//    /**
//     * Task 6.
//     * TODO: Check to see if the AppleState's holder field is included in the participants.
//     */
//    @Test
//    void holderIsParticipant() throws NoSuchAlgorithmException {
//
//        ArrayList<PublicKey> publicKeys = new ArrayList<>();
//
//        KeyPair issuerKeys = TestUtils.generateKey();
//        publicKeys.add(issuerKeys.getPublic());
//
//        KeyPair holderKeys = TestUtils.generateKey();
//        publicKeys.add(holderKeys.getPublic());
//
//        AppleStamp appleStamp = new AppleStamp(UUID.randomUUID(), "This is a test stamp.",
//                issuerKeys.getPublic(), holderKeys.getPublic());
//
//        /* リストに期待したオブジェクトが含まれない場合、indexOfメソッドは-1を返す. */
//        assertNotEquals(appleStamp.getParticipants().indexOf(holderKeys.getPublic()), -1);
//    }

}