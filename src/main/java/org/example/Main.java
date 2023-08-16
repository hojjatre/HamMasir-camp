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
        BufferedReader accountReader;
        String row = "";

//        List<Account> accounts = utilities.readAccounts();
        List<Account> accounts = new ArrayList<>();
        BufferedReader transactionReader;

        try {
            accountReader = new BufferedReader(new FileReader("C:\\Users\\hojja\\IdeaProjects\\HamMasir-camp-project\\src\\main\\java\\org\\example\\Race Conditions - Accounts.csv"));
            while (( row = accountReader.readLine()) != null){
                String[] data = row.split(",");
                if (data[0].equals("ID")) continue;
                Long amount = Long.parseLong(data[2].replace("\"", "") +  data[3].replace("\"", "") + data[4].replace("\"", ""));
                accounts.add(
                        new Account(Integer.parseInt(data[0]), data[1], amount));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        List<Transaction> transactions = utilities.readTransaction();
        List<Transaction> transactions = new ArrayList<>();
        try {
            transactionReader = new BufferedReader(new FileReader("C:\\Users\\hojja\\IdeaProjects\\HamMasir-camp-project\\src\\main\\java\\org\\example\\Race Conditions - Transaction.csv"));
            while (( row = transactionReader.readLine()) != null){
                String[] data = row.split(",");
                if (data[0].equals("FromAccount")) continue;
                Long amount = Long.parseLong(data[2].replace("\"", "") + "" +  data[3].replace("\"", ""));
                transactions.add(new Transaction(accounts.get(Integer.parseInt(data[0]) - 1),
                        accounts.get(Integer.parseInt(data[1]) - 1), amount));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Number of thread : 5
        CustomThreadPool threadPool = new CustomThreadPool(5);

        for (Transaction transaction:transactions) {
            threadPool.submit(transaction);
        }
        threadPool.shutdown();

        while (!threadPool.isTerminated());

        System.out.println("Final accounts:");
        for (Account account: accounts) {
            System.out.println(account.getId() + ", " + account.getName() + ", " + account.getName());
        }
    }
}