package com.toyblockchain.storage;

import org.paumard.streams.StreamsUtils;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class MerkleTree {

    private Node root;
    private Map<String, Node> leaves = new HashMap<>();

    private MerkleTree() {
    }

    public static MerkleTree of(String... contents) {
        MerkleTree tree = new MerkleTree();

        // the leaves
        List<Node> level = Arrays.stream(contents)
                .map(Node::new)
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
                    .map(list -> new Node(list.get(0), list.get(1)))
                    .collect(toList());
        }

        tree.root = level.get(0);
        return tree;
    }

    public Optional<List<String>> getPath(String hash) {
        Node node = leaves.get(hash);
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
