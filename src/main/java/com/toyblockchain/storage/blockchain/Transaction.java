package com.toyblockchain.storage.blockchain;

public class Transaction {

    private String plainText;

    public Transaction(String plainText) {
        this.plainText = plainText;
    }

    public String getPlainText() {
        return plainText;
    }
}
