package com.toyblockchain.actors;

import com.toyblockchain.crypto.KeyOperations;

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
    }

    /**
     * @return the compressed public key
     */
    public String getPublicKey() {
        if (publicKey == null) {
            publicKey = KeyOperations.calculatePublicKey(keyPair);
        }
        return publicKey;
    }

    /**
     * @return the wallet address
     */
    public String getAddress() {
        if (address == null) {
            address = KeyOperations.calculateAddress(getPublicKey());
        }
        return address;
    }

    /**
     * @return a Wallet with a randomly generated key pair
     */
    public static Wallet generate() {
        KeyPair keyPair = KeyOperations.generateKeyPair();
        return new Wallet(keyPair);
    }
}
