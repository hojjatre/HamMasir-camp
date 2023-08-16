package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        while (!threadPool.isTerminated());

        System.out.println("Final accounts:");
        for (Account account: accounts) {
            System.out.println(account.getId() + ", " + account.getName() + ", " + account.getBalance());
        }
    }
}