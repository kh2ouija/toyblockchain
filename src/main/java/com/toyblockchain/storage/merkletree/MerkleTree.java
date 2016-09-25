package com.toyblockchain.storage.merkletree;

import org.paumard.streams.StreamsUtils;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class MerkleTree {

    private MerkleNode root;
    private Map<String, MerkleNode> leaves = new HashMap<>();

    private MerkleTree() {
    }

    public static MerkleTree of(List<String> values) {
        return buildMerkleTree(values);
    }

    public static MerkleTree of(String... values) {
        return buildMerkleTree(Arrays.asList(values));
    }

    private static MerkleTree buildMerkleTree(List<String> values) {
        MerkleTree tree = new MerkleTree();

        // the leaves
        List<MerkleNode> level = values.stream()
                .map(MerkleNode::new)
                .peek(node -> tree.leaves.put(node.getHash(), node))
                .collect(toList());

        // pairing loop
        while (level.size() != 1) {
            // if uneven nodes, last one gets paired with self
            if (level.size() % 2 != 0) {
                level.add(level.get(level.size() - 1));
            }
            level = StreamsUtils.group(level.stream(), 2)
                    .map(pairStream -> pairStream.collect(toList()))
                    .map(list -> new MerkleNode(list.get(0), list.get(1)))
                    .collect(toList());
        }

        tree.root = level.get(0);
        return tree;
    }

    public MerkleNode getRootNode() {
        return root;
    }

    public Optional<List<String>> getPath(String hash) {
        MerkleNode node = leaves.get(hash);
        if (node != null) {
            List<String> path = new ArrayList<>();
            while (!node.equals(root)) {
                path.add(node.getSibling().getHash());
                node = node.getParent();
            }
            return Optional.of(path);
        } else {
            return Optional.empty();
        }
    }

}
