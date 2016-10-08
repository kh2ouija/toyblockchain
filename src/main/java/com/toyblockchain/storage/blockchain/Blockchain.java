package com.toyblockchain.storage.blockchain;

import java.util.*;

public class Blockchain {

    private List<Block> chain = new ArrayList<>();
    private Map<byte[], Block> index = new HashMap<>();

    public void offer(Block block) {
        chain.add(block);
        index.put(block.getBlockHeaderHash(), block);
    }

    public int getHeight() {
        return chain.size();
    }

    public Block getLatest() {
        return chain.get(chain.size() - 1);
    }
}
