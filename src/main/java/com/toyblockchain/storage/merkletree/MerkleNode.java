package com.toyblockchain.storage.merkletree;

import static com.toyblockchain.crypto.Encodings.hex;
import static com.toyblockchain.crypto.Digests.sha256;

public class MerkleNode {

    private MerkleNode parent;
    private MerkleNode left;
    private MerkleNode right;
    private byte[] hash;

    /**
     * Leaf node
     */
    MerkleNode(byte[] hash) {
        this.hash = hash;
    }

    /**
     * Branch Node
     */
    MerkleNode(MerkleNode node1, MerkleNode node2) {
        this.left = node1;
        this.left.parent = this;
        this.right = node2;
        this.right.parent = this;

        byte[] hashes = new byte[32 + 32];
        System.arraycopy(left.hash, 0, hashes, 0, 32);
        System.arraycopy(right.hash, 0, hashes, 32, 32);
        this.hash = sha256(hashes);
    }

    MerkleNode getSibling() {
        if (parent == null) {
            return null;
        }
        else {
            if (parent.left.equals(this)) {
                return parent.right;
            } else {
                return parent.left;
            }
        }
    }

    public byte[] getHash() {
        return hash;
    }

    MerkleNode getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return hex(hash);
    }
}
