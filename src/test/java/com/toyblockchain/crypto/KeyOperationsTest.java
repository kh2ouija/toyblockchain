package com.toyblockchain.crypto;

import org.junit.Before;

import java.security.Security;

public class KeyOperationsTest {

    @Before
    public void setUp() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

}