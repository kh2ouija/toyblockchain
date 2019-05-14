package com.toyblockchain.storage.blockchain;

import java.util.*;

import static com.toyblockchain.crypto.Encodings.hex;

public class Blockchain {

    private List<Block> chain = new LinkedList<>();
    private Map<String, Block> index = new HashMap<>();

    public void offer(Block block) {
        chain.add(block);
        index.put(hex(block.getBlockHeaderHash()), block);
    }

    public int getHeight() {
        return chain.size();
    }

    public Block getLatest() {
        return chain.get(chain.size() - 1);
    }
}
