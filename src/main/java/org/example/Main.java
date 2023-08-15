package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Map<Integer, Transaction> transactionMap = new HashMap<>();
        HashMap<Integer, Long> accountMap = new HashMap<>();
        BufferedReader transactions;
        BufferedReader accounts;
        String row = "";
        int index = 0;


        // read Transaction
        try {
            transactions = new BufferedReader(new FileReader("C:\\Users\\hojja\\IdeaProjects\\HamMasir-camp-project\\src\\main\\java\\org\\example\\Race Conditions - Transaction.csv"));
            while (( row = transactions.readLine()) != null){
                String[] data = row.split(",");
                if (data[0].equals("FromAccount")) continue;
                Long amount = Long.parseLong(data[2].replace("\"", "") + "" +  data[3].replace("\"", ""));
                transactionMap.put(index, new Transaction(Integer.parseInt(data[0]), Integer.parseInt(data[1]), amount));
                index += 1;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //read Accounts
        try {
            accounts = new BufferedReader(new FileReader("C:\\Users\\hojja\\IdeaProjects\\HamMasir-camp-project\\src\\main\\java\\org\\example\\Race Conditions - Accounts.csv"));
            while (( row = accounts.readLine()) != null){
                String[] data = row.split(",");
                if (data[0].equals("ID")) continue;
                Long amount = Long.parseLong(data[2].replace("\"", "") +  data[3].replace("\"", "") + data[4].replace("\"", ""));
                accountMap.put(Integer.parseInt(data[0]), amount);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Utilities utilities = new Utilities();
        utilities.createThread(500, transactionMap.size());
        List<Thread> threads = utilities.splitMapByThreadNum(transactionMap, accountMap);
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).start();
        }

        for (int i = 0; i < threads.size(); i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        HashMap<Integer, Long> accountMapBalance = utilities.getBalancingThread().balance;

        for (int ind: accountMap.keySet()) {
            System.out.println(ind + " " + accountMapBalance.get(ind).toString());
        }
    }
}