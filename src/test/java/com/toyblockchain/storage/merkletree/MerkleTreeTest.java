package com.toyblockchain.storage.merkletree;

import com.toyblockchain.Fixtures;
import com.toyblockchain.crypto.Encodings;
import com.toyblockchain.crypto.Digests;
import com.toyblockchain.storage.blockchain.Transaction;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.Security;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MerkleTreeTest {


    @Before
    public void setUp() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    @Test
    public void firstTest() {
        List<Transaction> transactions = Fixtures.createTransactions();
        List<String> txHashes = transactions.stream()
                .map(Transaction::toString)
                .map(Digests::sha256)
                .map(Encodings::hex)
                .collect(Collectors.toList());

        MerkleTree tree = MerkleTree.of(txHashes);
        txHashes.forEach(txHash -> assertEquals(4, tree.getPath(txHash).get().size()));
        assertFalse(tree.getPath(DigestUtils.sha256Hex("foobar")).isPresent());
    }

}