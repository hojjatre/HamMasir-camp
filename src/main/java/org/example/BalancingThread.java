package org.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BalancingThread extends Thread{
    protected HashMap<Integer, Long> balance = null;
    protected List<Map<Integer, Transaction>> transaction = null;

    public BalancingThread(List<Map<Integer, Transaction>> chunks, HashMap<Integer, Long> balance){
        this.transaction = chunks;
        this.balance = balance;
    }


    @Override
    public  void run() {
        calculator();
    }

    public synchronized void calculator(){
        // Select which THREAD run this.
        Map<Integer, Transaction> temp = transaction.get(
                Integer.parseInt(Thread.currentThread().getName()));
        // About every 500 transactions are in one list.
        // Key's list is a ROW of data, so it could be any number to solve I have used min and max in Collections
        for (int i = Collections.min(temp.keySet()); i <= Collections.max(temp.keySet()); i++) {
            int fromAccount = temp.get(i).getFromAcccount();
            int toAccount = temp.get(i).getToAccount();
            long balanceFromAccount = balance.get(fromAccount);
            long balanceToAccount = balance.get(toAccount);
            balance.put(fromAccount, balanceFromAccount - temp.get(i).getAmount());
            balance.put(toAccount, balanceToAccount + temp.get(i).getAmount());
        }
    }

    public HashMap<Integer, Long> getBalance() {
        return balance;
    }

}
