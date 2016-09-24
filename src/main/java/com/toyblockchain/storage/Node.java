package com.toyblockchain.storage;

import org.apache.commons.codec.digest.DigestUtils;

public class Node {

    private Node parent;
    private Node left;
    private Node right;
    private String hash;

    /**
     * Leaf node
     */
    Node(String content) {
        this.hash = DigestUtils.sha256Hex(content);
    }

    /**
     * Branch Node
     */
    Node(Node node1, Node node2) {
        this.left = node1;
        this.left.parent = this;
        this.right = node2;
        this.right.parent = this;
        this.hash = DigestUtils.sha256Hex(left.hash + right.hash);
    }

    Node getSibling() {
        if (parent == null) {
            return null;
        }
        else {
            if (parent.left.equals(this)) {
                return parent.right;
            } else {
                return parent.left;
            }
        }
    }

    String getHash() {
        return hash;
    }

    Node getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return hash;
    }
}
