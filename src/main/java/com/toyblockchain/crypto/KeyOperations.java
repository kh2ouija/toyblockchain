package com.toyblockchain.crypto;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.math.ec.ECPoint;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;

import static com.toyblockchain.crypto.Encodings.base58;
import static com.toyblockchain.crypto.Encodings.hex;
import static com.toyblockchain.crypto.Hashes.ripemd160;
import static com.toyblockchain.crypto.Hashes.sha256;

public class KeyOperations {

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
            keyPairGenerator.initialize(new ECGenParameterSpec("secp256k1"), new SecureRandom());
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    public static String calculatePublicKey(KeyPair keyPair) {
        ECPoint q = ((BCECPublicKey) keyPair.getPublic()).getQ();
        byte[] pubBytes = new byte[1 + 32 + 32];
        pubBytes[0] = 0x04;
        System.arraycopy(q.getXCoord().getEncoded(), 0, pubBytes, 1, 32);
        System.arraycopy(q.getYCoord().getEncoded(), 0, pubBytes, 1 + 32, 32);
        return hex(pubBytes);
    }

    public static String calculateAddress(String publicKey) {
        byte[] keyBytes;
        try {
            keyBytes = Hex.decodeHex(publicKey.toCharArray());
        } catch (DecoderException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] payload = ripemd160(sha256(keyBytes)); // 20 bytes
        byte[] addressBytes = new byte[1 + 20 + 4]; // version + payload + checksum
        addressBytes[0] = 0x00; // version
        System.arraycopy(payload, 0, addressBytes, 1, 20); // payload
        byte[] preChecksum = Arrays.copyOfRange(addressBytes, 0, 21);
        byte[] checksum = Arrays.copyOfRange(sha256(sha256(preChecksum)), 0, 4); // first 4 bytes
        System.arraycopy(checksum, 0, addressBytes, 21, 4); // checksum
        return base58(addressBytes);
    }

}
