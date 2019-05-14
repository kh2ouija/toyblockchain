package com.toyblockchain.storage.merkletree;

import com.toyblockchain.crypto.Encodings;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MerkleTree {

    private MerkleNode root;
    private Map<String, MerkleLeaf> leaves;

    MerkleTree(MerkleNode root, Map<String, MerkleLeaf> leaves) {
        this.root = root;
        this.leaves = leaves;
    }

    public MerkleNode getRootNode() {
        return root;
    }

    public MerkleLeaf getLeaf(String hash) {
        return leaves.get(hash);
    }

    public Optional<List<String>> getPath(String hash) {
        return Optional.ofNullable(getLeaf(hash)).map(node ->
                Stream.iterate(node, MerkleNode::hasParent, MerkleNode::getParent)
                        .map(MerkleNode::getSibling)
                        .map(MerkleNode::getHash)
                        .map(Encodings::hex)
                        .collect(Collectors.toList())
        );
    }
}
