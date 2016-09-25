package com.toyblockchain.actors;

import org.junit.Before;
import org.junit.Test;

import java.security.Security;

public class WalletTest {

    @Before
    public void setUp() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    @Test
    public void name() throws Exception {
        Wallet wallet = Wallet.generate();
        System.out.println("pub: " + wallet.getPublicKey());
        System.out.println("address: " + wallet.getAddress());
    }

}