package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Utilities {

    private List<Thread> threads = new ArrayList<>();
    private int numberThread;
    private int chunkSize;

    BalancingThread balancingThread;
    public void createThread(int chunkSize, int sizeData){
        this.chunkSize = chunkSize;
        this.numberThread = (int) Math.ceil(sizeData/chunkSize) + 1;
    }

    public List<Thread> splitMapByThreadNum(Map<Integer, Transaction> transactionMap,  HashMap<Integer, Long> balance){
        // Split Map to equal CHUNKs
        List<Map<Integer, Transaction>> chunks = transactionMap.entrySet()
                .stream().collect(Collectors.groupingBy(entry -> (entry.getKey() - 1) / this.chunkSize))
                .values().stream()
                .map(list -> list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .collect(Collectors.toList());
        balancingThread = new BalancingThread(chunks, balance);
        //Create our Threads
        for (int i = 0; i < numberThread; i++) {
            Thread thread = new Thread(balancingThread, String.valueOf(i));
            threads.add(thread);
        }
        return threads;
    }

    public BalancingThread getBalancingThread() {
        return balancingThread;
    }
}
