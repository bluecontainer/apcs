package com.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

public class BlockchainUnitTest {

    public static List<Block> blockchain = new ArrayList<Block>();
    public static int prefix = 4;
    public static String prefixString = new String(new char[prefix]).replace('\0', '0');

    @BeforeAll
    public static void setUp() {
        Block genesisBlock = new Block("The is the Genesis Block.", "0", new Date().getTime());
        genesisBlock.mineBlock(prefix);
        blockchain.add(genesisBlock);
        Block firstBlock = new Block("The is the First Block.", genesisBlock.getHash(), new Date().getTime());
        firstBlock.mineBlock(prefix);
        blockchain.add(firstBlock);
    }

    @Test
    public void givenBlockchain_whenNewBlockAdded_thenSuccess() {
        Block newBlock = new Block("The is a New Block.", blockchain.get(blockchain.size() - 1)
            .getHash(), new Date().getTime());
        newBlock.mineBlock(prefix);
        assertTrue(newBlock.getHash()
            .substring(0, prefix)
            .equals(prefixString));
        blockchain.add(newBlock);
    }

    @Test
    public void givenBlockchain_whenValidated_thenSuccess() {
        boolean flag = true;
        for (int i = 0; i < blockchain.size(); i++) {
            String previousHash = i == 0 ? "0"
                : blockchain.get(i - 1)
                    .getHash();
            flag = blockchain.get(i)
                .getHash()
                .equals(blockchain.get(i)
                    .calculateBlockHash())
                && previousHash.equals(blockchain.get(i)
                    .getPreviousHash())
                && blockchain.get(i)
                    .getHash()
                    .substring(0, prefix)
                    .equals(prefixString);
            if (!flag)
                break;
        }
        assertTrue(flag);
    }

    @Test
    public void createBlockchain_addBlock() {
        Blockchain chain = new Blockchain(4);
        Block genesisBlock = new Block("The is the Genesis Block.", new Date().getTime());
        chain.addBlock(genesisBlock);
        Block firstBlock = new Block("The is the First Block.", new Date().getTime());
        chain.addBlock(firstBlock);
        assertTrue(chain.blockchain.size() == 2);
        assertTrue(chain.blockchain.get(0).getHash() == chain.blockchain.get(1).getPreviousHash());
    }

    @AfterAll
    public static void tearDown() {
        blockchain.clear();
    }

}
