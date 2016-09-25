package com.toyblockchain.actors;

import com.toyblockchain.crypto.Encodings;
import com.toyblockchain.crypto.Hashes;
import com.toyblockchain.storage.blockchain.Block;
import com.toyblockchain.storage.blockchain.Blockchain;
import com.toyblockchain.storage.blockchain.Transaction;
import com.toyblockchain.storage.merkletree.MerkleTree;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Client {

    private final Blockchain blockchain;

    public Client(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public void mineBlock(List<Transaction> transactions) {
        Block block = new Block();
        block.setPreviousBlock(blockchain.getLatest());
        List<String> txHashes = transactions.stream()
                .map(Transaction::getPlainText)
                .map(Hashes::sha256)
                .map(Encodings::hex)
                .collect(Collectors.toList());
        block.setMerkleRoot(MerkleTree.of(txHashes).getRootNode());
        block.setTime(LocalDateTime.now());
        blockchain.offer(block);
    }

}
