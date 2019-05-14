package com.toyblockchain.storage.merkletree;

import static com.toyblockchain.crypto.Encodings.hex;
import static com.toyblockchain.crypto.Digests.sha256;

public class MerkleNode {

    private MerkleNode parent;
    private MerkleNode left;
    private MerkleNode right;
    byte[] hash;

    MerkleNode() {}

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

    boolean hasParent() {
        return (parent != null);
    }

    public MerkleNode getParent() {
        return parent;
    }

    public void setParent(MerkleNode parent) {
        this.parent = parent;
    }

    public MerkleNode getLeft() {
        return left;
    }

    public void setLeft(MerkleNode left) {
        this.left = left;
    }

    public MerkleNode getRight() {
        return right;
    }

    public void setRight(MerkleNode right) {
        this.right = right;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return hex(hash);
    }
}
