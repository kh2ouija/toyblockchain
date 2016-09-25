package com.toyblockchain.crypto;

import org.junit.Before;
import org.junit.Test;

import java.security.Security;

import static org.junit.Assert.assertEquals;

public class KeyOperationsTest {

    @Before
    public void setUp() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    @Test
    public void generateKeyPair() throws Exception {

    }

    @Test
    public void calculatePublicKey() throws Exception {
    }

    @Test
    public void testAddressGenerationFromPublicKey() throws Exception {
        String publicKey = "0450863AD64A87AE8A2FE83C1AF1A8403CB53F53E486D8511DAD8A04887E5B23522CD470243453A299FA9E77237716103ABC11A1DF38855ED6F2EE187E9C582BA6";
        assertEquals("16UwLL9Risc3QfPqBUvKofHmBQ7wMtjvM", KeyOperations.calculateAddress(publicKey));
    }

}