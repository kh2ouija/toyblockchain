package com.toyblockchain.storage.merkletree;

import com.toyblockchain.crypto.Digests;
import com.toyblockchain.storage.blockchain.Transaction;
import org.paumard.streams.StreamsUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.toyblockchain.crypto.Digests.sha256;
import static com.toyblockchain.crypto.Encodings.hex;
import static java.util.stream.Collectors.toList;

public class MerkleTreeBuilder {

    public MerkleTree buildTreeFor(List<Transaction> transactions) {
        return buildMerkleTree(
                transactions.stream()
                        .map(Transaction::toString)
                        .collect(Collectors.toList())
        );
    }

    private MerkleTree buildMerkleTree(List<String> values) {
        // the leaves
        Map<String, MerkleLeaf> leaves = values.stream()
                .map(Digests::sha256)
                .map(MerkleLeaf::new)
                .collect(Collectors.toMap(leaf -> hex(leaf.getHash()), Function.identity()));

        // pairing loop
        List<MerkleNode> level = new ArrayList<>(leaves.values());
        while (level.size() != 1) {
            // if uneven nodes, last one gets paired with self
            if (level.size() % 2 != 0) {
                level.add(level.get(level.size() - 1));
            }
            level = StreamsUtils.group(level.stream(), 2)
                    .map(pairStream -> pairStream.collect(toList()))
                    .map(list -> buildParentNode(list.get(0), list.get(1)))
                    .collect(toList());
        }
        MerkleNode root = level.get(0);

        return new MerkleTree(root, leaves);
    }

    private MerkleNode buildParentNode(MerkleNode node1, MerkleNode node2) {
        MerkleNode parent = new MerkleNode();
        parent.setLeft(node1);
        node1.setParent(parent);
        parent.setRight(node2);
        node2.setParent(parent);

        byte[] hashes = new byte[32 + 32];
        System.arraycopy(parent.getLeft().getHash(), 0, hashes, 0, 32);
        System.arraycopy(parent.getRight().getHash(), 0, hashes, 32, 32);
        parent.setHash(sha256(hashes));
        return parent;
    }


}
