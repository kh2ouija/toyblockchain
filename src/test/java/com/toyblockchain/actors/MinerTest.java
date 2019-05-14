package com.toyblockchain.actors;

import com.toyblockchain.Fixtures;
import com.toyblockchain.storage.blockchain.Block;
import com.toyblockchain.storage.blockchain.Blockchain;
import com.toyblockchain.storage.blockchain.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.security.Security;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MinerTest {

    private Block genesis;
    private Blockchain blockchain;
    private Miner miner;

    @Before
    public void setUp() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        genesis = new Block(0, ByteBuffer.allocate(32).array(), Collections.singletonList(new Transaction("Alice", "Bob", 1)));
        System.out.println(genesis);
        blockchain = new Blockchain();
        blockchain.offer(genesis);
        miner = new Miner(blockchain);
    }

    @Test
    public void mineBlock() {
        List<Transaction> transactions = Fixtures.createTransactions();
        Block block = miner.mineBlock(transactions);
        System.out.println(block);
        assertEquals(2, blockchain.getHeight());
        assertEquals(genesis.getBlockHeaderHash(), blockchain.getLatest().getPreviousBlockHash());
    }

}