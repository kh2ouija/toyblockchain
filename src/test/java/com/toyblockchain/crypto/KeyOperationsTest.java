package com.toyblockchain.crypto;

import org.junit.Before;
import org.junit.Test;

import java.security.Security;

import static com.toyblockchain.crypto.Digests.sha256;

public class KeyOperationsTest {

    @Before
    public void setUp() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

}