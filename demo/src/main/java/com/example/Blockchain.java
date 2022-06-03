package com.example;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    public List<Block> blockchain = new ArrayList<Block>();
    public int prefix;

    Blockchain(int prefix) {
        this.prefix = prefix;
    }

    boolean addBlock(Block block) {
        if (blockchain.size() == 0) {
            block.setPreviousHash("0");
        } else {
            Block lastBlock = blockchain.get(blockchain.size() - 1);
            block.setPreviousHash(lastBlock.getHash());
        }

        block.mineBlock(prefix);
        blockchain.add(block);
        return true;
    }
}
