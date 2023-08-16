package org.example;

class Transaction implements Runnable {
    private Account fromAccount;
    private Account toAccount;
    private long amount;

    public Transaction(Account fromAccount, Account toAccount, long amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    @Override
    public void run() {
        toAccount.deposit(amount);
        fromAccount.withdraw(amount);
    }
}

