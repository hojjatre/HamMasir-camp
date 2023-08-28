package org.example.project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Utilities {
    private List<Account> accounts;
    private List<Transaction> transactions;

    public Utilities() {
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    public List<Account> readAccounts(){
        BufferedReader accountReader;
        String row = "";

        try {
            accountReader = new BufferedReader(new FileReader("C:\\Users\\hojja\\IdeaProjects\\HamMasir-camp-project\\src\\main\\java\\org\\example\\Race Conditions - Accounts.csv"));
            while (( row = accountReader.readLine()) != null){
                String[] data = row.split(",");
                if (data[0].equals("ID")) continue;
                Long amount = Long.parseLong(data[2].replace("\"", "") +  data[3].replace("\"", "") + data[4].replace("\"", ""));
                accounts.add(
                        new Account(Integer.parseInt(data[0]), data[1], new AtomicLong(amount)));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return accounts;
    }

    public List<Transaction> readTransaction(){
        BufferedReader transactionReader;
        String row = "";


        try {
            transactionReader = new BufferedReader(new FileReader("C:\\Users\\hojja\\IdeaProjects\\HamMasir-camp-project\\src\\main\\java\\org\\example\\Race Conditions - Transaction.csv"));
            while (( row = transactionReader.readLine()) != null){
                String[] data = row.split(",");
                if (data[0].equals("FromAccount")) continue;
                long amount = Long.parseLong(data[2].replace("\"", "") + "" +  data[3].replace("\"", ""));
                transactions.add(new Transaction(accounts.get(Integer.parseInt(data[0]) - 1),
                        accounts.get(Integer.parseInt(data[1]) - 1), new AtomicLong(amount)));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return transactions;
    }
}
