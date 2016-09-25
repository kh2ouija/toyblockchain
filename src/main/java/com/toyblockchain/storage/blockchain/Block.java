package com.toyblockchain.storage.blockchain;

import com.toyblockchain.storage.merkletree.MerkleNode;

import java.time.LocalDateTime;

public class Block {

    private Block previousBlock;
    private MerkleNode merkleRoot;
    private LocalDateTime time;

    public Block getPreviousBlock() {
        return previousBlock;
    }

    public void setPreviousBlock(Block previousBlock) {
        this.previousBlock = previousBlock;
    }

    public MerkleNode getMerkleRoot() {
        return merkleRoot;
    }

    public void setMerkleRoot(MerkleNode merkleRoot) {
        this.merkleRoot = merkleRoot;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
