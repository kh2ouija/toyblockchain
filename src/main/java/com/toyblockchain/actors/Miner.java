package com.toyblockchain.actors;

import com.toyblockchain.storage.blockchain.Block;
import com.toyblockchain.storage.blockchain.Blockchain;
import com.toyblockchain.storage.blockchain.Transaction;

import java.util.List;

/**
 * The miner packages transactions into blocks and adds them to the blockchain.
 */
public class Miner {

    private final Blockchain blockchain;

    public Miner(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public void mineBlock(List<Transaction> transactions) {
        Block block = prepareBlock(transactions, blockchain.getLatest().getBlockHeaderHash());
        blockchain.offer(block);
    }

    public Block prepareBlock(List<Transaction> transactions, byte[] previousBlockHash) {
        return new Block(0, previousBlockHash, transactions);
    }

}
