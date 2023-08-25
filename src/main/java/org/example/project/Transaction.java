package org.example.project;

import java.util.concurrent.atomic.AtomicLong;

class Transaction implements Runnable {
    private Account fromAccount;
    private Account toAccount;
    private AtomicLong amount;

    public Transaction(Account fromAccount, Account toAccount, AtomicLong amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    @Override
    public void run() {
        toAccount.deposit(amount.get());
        fromAccount.withdraw(amount.get());
        System.out.println("Transferred " + amount + " from " + fromAccount.getName() + " to " + toAccount.getName());
    }
}

