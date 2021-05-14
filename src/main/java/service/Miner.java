package service;

import domain.Block;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Miner {
    public static void main(String[] args) {
        List<Block> blockchain = new ArrayList<>();
        int prefix = 4;
        String prefixString = new String(new char[prefix]).replace('\0', '0');

        setUpGenesisBlock(blockchain, prefix);

        do{
            Block newBlock = new Block(
                    "The is a New Block.",
                    blockchain.get(blockchain.size() - 1).getHash(),
                    new Date().getTime());
            newBlock.mineBlock(prefix);
            if(newBlock.getHash().substring(0, prefix).equals(prefixString))
                blockchain.add(newBlock);

            printBlocks(blockchain);
        }while (1 == 1);

    }

    private static void setUpGenesisBlock(List<Block> blockchain, int prefix) {
        Block genesisBlock = new Block("The is the Genesis Block.", "0", new Date().getTime());
        genesisBlock.mineBlock(prefix);
        blockchain.add(genesisBlock);

        Block firstBlock = new Block("The is the First Block.", genesisBlock.getHash(), new Date().getTime());
        firstBlock.mineBlock(prefix);
        blockchain.add(firstBlock);
    }

    private static void printBlocks(List<Block> blockchain) {
        System.out.println(blockchain.size());
    }

    private static boolean validateBlocks(List<Block> blockchain, int prefix, String prefixString) {
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
        return flag;
    }
}
