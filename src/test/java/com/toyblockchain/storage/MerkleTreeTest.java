package com.toyblockchain.storage;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MerkleTreeTest {

    @Test
    public void firstTest() {
        MerkleTree tree = MerkleTree.of("Gabriel Oprea anunta ca demisioneaza din Senat".split(" "));
        assertEquals(3, tree.getPath(DigestUtils.sha256Hex("Oprea")).get().size());
        assertEquals(3, tree.getPath(DigestUtils.sha256Hex("demisioneaza")).get().size());
        assertFalse(tree.getPath(DigestUtils.sha256Hex("foobar")).isPresent());
    }

}