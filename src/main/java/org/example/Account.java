package org.example;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Account {
    private int id;
    private String name;
    private AtomicLong balance;
    private Lock lock = new ReentrantLock();

    public Account(int id, String name, AtomicLong balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AtomicLong getBalance() {
        return balance;
    }

    public void deposit(long amount) {
        balance.set(balance.get() + amount);
//        lock.lock();
//        try {
//            balance += amount;
//        } finally {
//            lock.unlock();
//        }
    }

    public void withdraw(long amount) {
        balance.set(balance.get() - amount);
//        lock.lock();
//        try {
//            balance -= amount;
//        } finally {
//            lock.unlock();
//        }
    }
}
