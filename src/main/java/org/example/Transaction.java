package org.example;

public class Transaction {
    private int fromAcccount;
    private int toAccount;
    private Long amount;

    public Transaction(int fromAcccount, int toAccount, Long amount) {
        this.fromAcccount = fromAcccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public int getFromAcccount() {
        return fromAcccount;
    }

    public void setFromAcccount(int fromAcccount) {
        this.fromAcccount = fromAcccount;
    }

    public int getToAccount() {
        return toAccount;
    }

    public void setToAccount(int toAccount) {
        this.toAccount = toAccount;
    }
}

