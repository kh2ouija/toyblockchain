package com.toyblockchain.storage.blockchain;

import java.util.*;

public class Blockchain {

    private List<Block> chain = new ArrayList<>();

    public void offer(Block block) {
        chain.add(block);
    }

    public Block getLatest() {
        return chain.get(chain.size() - 1);
    }
}
