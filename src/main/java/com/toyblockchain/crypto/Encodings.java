package com.toyblockchain.crypto;

import org.apache.commons.codec.binary.Hex;

public class Encodings {

    public static String hex(byte[] bytes) {
        return Hex.encodeHexString(bytes);
    }

    public static String base58(byte[] bytes) {
        return Base58.encode(bytes);
    }
}
