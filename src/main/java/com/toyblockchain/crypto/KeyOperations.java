package com.toyblockchain.crypto;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;

import static com.toyblockchain.crypto.Encodings.base58;
import static com.toyblockchain.crypto.Encodings.hex;
import static com.toyblockchain.crypto.Digests.ripemd160;
import static com.toyblockchain.crypto.Digests.sha256;

public class KeyOperations {

    /**
     * https://en.wikipedia.org/wiki/Elliptic_Curve_Digital_Signature_Algorithm
     */
    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
            keyPairGenerator.initialize(new ECGenParameterSpec("secp256k1"), new SecureRandom());
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Calculate the compressed public key (02x if y is even, 03x if y is odd)
     */
    public static String calculatePublicKey(KeyPair keyPair) {
        ECPoint q = ((BCECPublicKey) keyPair.getPublic()).getQ();
        byte[] pubBytes = new byte[1 + 32];
        if (q.getYCoord().toBigInteger().mod(BigInteger.valueOf(2L)).equals(BigInteger.ZERO)) {
            pubBytes[0] = 0x02;
        }
        else {
            pubBytes[0] = 0x03;
        }
        System.arraycopy(q.getXCoord().getEncoded(), 0, pubBytes, 1, 32);
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
        System.arraycopy(payload, 0, addressBytes, 1, 20); // append payload
        byte[] preChecksum = Arrays.copyOfRange(addressBytes, 0, 21);
        byte[] checksum = Arrays.copyOfRange(sha256(sha256(preChecksum)), 0, 4); // first 4 bytes
        System.arraycopy(checksum, 0, addressBytes, 21, 4); // append checksum
        return base58(addressBytes);
    }

}
