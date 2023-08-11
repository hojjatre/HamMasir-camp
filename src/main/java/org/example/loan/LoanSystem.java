package org.example.loan;

import org.example.bookmanagement.Book;
import org.example.catalogsystem.CatalogImpl;
import org.example.usermanagement.Member;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoanSystem {
    private List<Loan> loans = new ArrayList<>();
    private HashMap<Member, List<Book>> memberLOANbook = new HashMap<Member, List<Book>>();

    public void addLoan(Loan loan) {
        loans.add(loan);
        if (memberLOANbook.containsKey(loan.getMember())) {
            memberLOANbook.get(loan.getMember()).add(loan.getBook());
        }else {
            memberLOANbook.put(loan.getMember(), new ArrayList<Book>());
            memberLOANbook.get(loan.getMember()).add(loan.getBook());
        }
    }

    public int checkExpiration(LocalDate today, Loan loan) {
        return today.compareTo(loan.getExpirationDate());
    }

    public void updatePenalty(LocalDate today, Loan loan){
        if (checkExpiration(today, loan) < 0) {
            System.out.println
                    ("There is no need for penalty, because " + ChronoUnit.DAYS.between(loan.getExpirationDate(), today) + "left.");
        }
        else {
            loan.setPenalty( ( (int) ChronoUnit.DAYS.between(today, loan.getExpirationDate()) ) * 100);
            System.out.println
                    ("You must return the book in " + (int) ChronoUnit.DAYS.between(today, loan.getExpirationDate()) +
                            "days ago.");
        }
    }

    public void printMyLoanBooks(Member member){
        CatalogImpl.printBooks(memberLOANbook.get(member));
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public HashMap<Member, List<Book>> getMemberLOANbook() {
        return memberLOANbook;
    }

    public static void checkAllExpirationDate(List<Loan> allLoan, Member member, LocalDate today){
        for (Loan loan: allLoan) {
            if (loan.getMember().equals(member)) {
                if(today.compareTo(loan.getExpirationDate()) < 0){
                    System.out.println
                            ("Book " + loan.getBook().getID() + ": " + loan.getBook().getTitle() +
                                    ", loan date: " + loan.getExpirationDate() + ", No penalty because " +
                                    ChronoUnit.DAYS.between(loan.getExpirationDate(), today)*(-1) + " left.");
                } else if (today.compareTo(loan.getExpirationDate()) > 0) {
                    loan.setPenalty( ( (int) ChronoUnit.DAYS.between(today, loan.getExpirationDate()) ) * (-100));
                    System.out.println
                            ("Book " + loan.getBook().getID() + ": " + loan.getBook().getTitle() +
                                    ", loan date: " + loan.getExpirationDate() + ", penalty: " + loan.getPenalty() +
                                    "$ , You must return the book in " +
                                    ChronoUnit.DAYS.between(loan.getExpirationDate(), today) + " days ago.");
                }
            }
        }
    }
}
