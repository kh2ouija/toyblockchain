package com.toyblockchain.storage.merkletree;

import static com.toyblockchain.crypto.Encodings.hex;
import static com.toyblockchain.crypto.Hashes.sha256;

public class MerkleNode {

    private MerkleNode parent;
    private MerkleNode left;
    private MerkleNode right;
    private String hash;

    /**
     * Leaf node
     */
    MerkleNode(String hash) {
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
        this.hash = hex(sha256(left.hash + right.hash));
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

    String getHash() {
        return hash;
    }

    MerkleNode getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return hash;
    }
}
