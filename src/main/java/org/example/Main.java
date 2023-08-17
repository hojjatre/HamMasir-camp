package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Utilities utilities = new Utilities();

        List<Account> accounts = utilities.readAccounts();
        List<Transaction> transactions = utilities.readTransaction();

        // Number of thread : 5
        CustomThreadPool threadPool = new CustomThreadPool(5);

        for (Transaction transaction:transactions) {
            threadPool.submit(transaction);
        }
        threadPool.shutdown();
        try {
            threadPool.awaitTermination();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        System.out.println("\nFinal accounts:");
        for (Account account: accounts) {
            System.out.println(account.getId() + ", " + account.getName() + ", " + account.getBalance());
        }
    }
}