package com.toyblockchain.storage.blockchain;

import com.google.common.collect.ImmutableMap;
import com.google.gson.GsonBuilder;
import com.toyblockchain.storage.merkletree.MerkleTree;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;

import static com.toyblockchain.crypto.Digests.sha256;
import static com.toyblockchain.crypto.Encodings.hex;
import static java.util.stream.Collectors.toList;

public class Block {

    private byte[] header;
    private byte[] blockHeaderHash;

    // header
    private final int version;
    private final byte[] previousBlockHash;
    private final byte[] merkleRoot;
    private final long timestamp;
    private final int difficulty;
    private final int nonce;

    // body
    private final List<Transaction> transactions;

    public Block(int version, byte[] previousBlockHash, List<Transaction> transactions) {
        this.version = version;
        this.previousBlockHash = previousBlockHash;
        this.transactions = transactions;
        this.timestamp = new Date().getTime();
        this.difficulty = 0;
        this.nonce = 0;
        this.merkleRoot = computeMerkleRoot();
        this.header = computeHeader();
        this.blockHeaderHash = computeHeaderHash();
    }

    private byte[] computeMerkleRoot() {
        List<String> transactionsPlaintext = transactions.stream()
                .map(Transaction::toString)
                .collect(toList());
        return MerkleTree.of(transactionsPlaintext).getRootNode().getHash();
    }

    private byte[] computeHeader() {
        byte[] header = new byte[84];

        int pos = 0;
        // 4 bytes: version
        System.arraycopy(ByteBuffer.allocate(4).putInt(version).array(), 0, header, pos, 4);
        pos += 4;
        // 32 bytes: previousBlockHash
        System.arraycopy(previousBlockHash, 0 , header, pos, 32);
        pos += 32;
        // 32 bytes: merkle root
        System.arraycopy(merkleRoot, 0, header, pos, 32);
        pos += 32;
        // 8 bytes: timestamp (original bitcoin is 4 bytes)
        System.arraycopy(ByteBuffer.allocate(8).putLong(timestamp).array(), 0, header, pos, 8);
        pos +=  8;
        // 4 bytes: difficulty
        System.arraycopy(ByteBuffer.allocate(4).putInt(difficulty).array(), 0, header, pos, 4);
        pos += 4;
        // 4 bytes: nonce
        System.arraycopy(ByteBuffer.allocate(4).putInt(nonce).array(), 0, header, pos, 4);

        return header;
    }

    private byte[] computeHeaderHash() {
        return sha256(sha256(header));
    }

    public byte[] getBlockHeaderHash() {
        return blockHeaderHash;
    }

    public byte[] getPreviousBlockHash() {
        return previousBlockHash;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(ImmutableMap.of(
                "previousBlockHash", hex(previousBlockHash),
                "merkleRoot", hex(merkleRoot),
                "time", timestamp,
                "transactions", transactions)
        );
    }
}
