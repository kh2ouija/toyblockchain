package com.toyblockchain.storage.merkletree;

public class MerkleLeaf extends MerkleNode {

    public MerkleLeaf(byte[] hash) {
        this.hash = hash;
    }

}
