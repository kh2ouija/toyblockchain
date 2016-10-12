package com.toyblockchain.actors;

import com.toyblockchain.crypto.KeyTools;

import java.security.KeyPair;

/**
 * Single address wallet
 */
public class Wallet {

    private KeyPair keyPair;

    // computed from keypair
    private String publicKey;
    private String address;

    private Wallet(KeyPair keyPair) {
        this.keyPair = keyPair;
        // no need for lazy initialization
        this.publicKey = KeyTools.calculatePublicKey(keyPair);
        this.address = KeyTools.calculateAddress(getPublicKey());
    }

    /**
     * @return the compressed public key
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * @return the wallet address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return a Wallet with a randomly generated key pair
     */
    public static Wallet generate() {
        return new Wallet(KeyTools.generateKeyPair());
    }
}
