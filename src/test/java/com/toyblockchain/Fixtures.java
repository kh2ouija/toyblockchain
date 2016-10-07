package com.toyblockchain;

import com.toyblockchain.storage.blockchain.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Fixtures {

    private static List<String> names = Arrays.asList("Alice", "Bob", "Carol", "Dave", "Eve", "Frank", "Grace", "Mallory", "Oscar", "Peggy");
    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    public static List<String> createNames() {
        return names;
    }

    public static List<Transaction> createTransactions() {
        return IntStream.range(0, 10).mapToObj(i ->
                new Transaction(names.get(random.nextInt(names.size())), names.get(random.nextInt(names.size())), random.nextInt(1, 100))
        ).collect(Collectors.toList());
    }

}
