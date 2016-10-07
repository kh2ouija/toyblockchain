package com.toyblockchain.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Digests {

    private static final MessageDigest sha256;
    private static final MessageDigest ripemd160;

    static {
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
            ripemd160 = MessageDigest.getInstance("RIPEMD160", "BC");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] sha256(String input) {
        return sha256(toBytes(input));
    }

    public static byte[] sha256(byte[] bytes) {
        return sha256.digest(bytes);
    }

    public static byte[] ripemd160(byte[] bytes) {
        return ripemd160.digest(bytes);
    }

    private static byte[] toBytes(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }

}
