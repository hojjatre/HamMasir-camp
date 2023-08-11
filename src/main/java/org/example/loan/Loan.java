package org.example.loan;

import org.example.bookmanagement.Book;
import org.example.usermanagement.Member;

import java.time.LocalDate;

public class Loan {
    private LocalDate expirationDate;
    private Member member;
    private Book book;
    private int penalty;
    public Loan(LocalDate expirationDate, Member member, Book book) {
        this.expirationDate = expirationDate;
        this.member = member;
        this.book = book;
        this.penalty = 0;
        this.book.setAvailableCopies(this.book.getAvailableCopies() - 1);
    }

    public Loan(int year, int month, int day, Member member, Book book){
        this.expirationDate = LocalDate.parse(year + "-" + month + "-" + day);
        this.member = member;
        this.book = book;
        this.penalty = 0;
        this.book.setAvailableCopies(this.book.getAvailableCopies() - 1);
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }
}
