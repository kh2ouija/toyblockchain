package com.toyblockchain.storage.merkletree;

import com.toyblockchain.crypto.Encodings;
import com.toyblockchain.storage.blockchain.Transaction;
import com.toyblockchain.crypto.Hashes;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MerkleTreeTest {

    @Test
    public void firstTest() {
        List<String> txHashes = Arrays.stream("Gabriel Oprea anunta ca demisioneaza din Senat".split(" "))
                .map(Transaction::new)
                .map(Transaction::getPlainText)
                .map(Hashes::sha256)
                .map(Encodings::hex)
                .collect(Collectors.toList());

        MerkleTree tree = MerkleTree.of(txHashes);
        txHashes.forEach(txHash -> assertEquals(3, tree.getPath(txHash).get().size()));
        assertFalse(tree.getPath(DigestUtils.sha256Hex("foobar")).isPresent());
    }

}