package org.example.loan;

import org.example.bookmanagement.Book;
import org.example.usermanagement.Member;

import java.time.LocalDate;

public class Loan {
    private LocalDate date;
    private Member member;
    private Book book;
    private int penalty;
    public Loan(LocalDate date, Member member, Book book) {
        this.date = date;
        this.member = member;
        this.book = book;
        this.penalty = 0;
    }

    public Loan(int year, int month, int day, Member member, Book book){
        this.date = LocalDate.parse(year + "-" + month + "-" + day);
        this.member = member;
        this.book = book;
        this.penalty = 0;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
