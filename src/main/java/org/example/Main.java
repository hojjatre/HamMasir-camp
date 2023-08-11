package org.example;

import org.example.bookmanagement.Author;
import org.example.bookmanagement.Book;
import org.example.bookmanagement.Genre;
import org.example.catalogsystem.CatalogImpl;
import org.example.loan.Loan;
import org.example.loan.LoanSystem;
import org.example.usermanagement.Librarian;
import org.example.usermanagement.Member;
import org.example.usermanagement.User;
import org.example.usermanagement.UserSystem;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        UserSystem userSystem = CreateData.createMember();
        CatalogImpl catalog = CreateData.createBook();
        LoanSystem loanSystem = new LoanSystem();
        User user = null;

        System.out.println("You are a Member(1) or Librarian(2): ");
        Scanner scanner = new Scanner(System.in);
        int memberORlibrarian = Integer.parseInt(scanner.nextLine());

        System.out.println("Sign in / Sing up:1/2: ");
        String signInORsignUp = scanner.nextLine();

        Security security = new Security(userSystem, scanner);

        if (signInORsignUp.equals("1")){
            user = security.signIn();
        }
        else {
            user = security.signUp(memberORlibrarian);
        }
        int exit = 0;
        while (exit == 0){
            if(security.authenticated){
                System.out.println
                        ("Search by title(1) ,Search by Author(2), " +
                                "All Books(3), Loan book(4), See my loan books(5), " +
                                "Check my expiration's loan books(6), EXIT(7): ");
                String choose = scanner.nextLine();
                if(choose.equals("1")){
                    System.out.println("Enter book's name: ");
                    String name = scanner.nextLine();
                    Book findBook = catalog.findBookByName(name);
                    if(findBook != null){
                        System.out.println(findBook.getTitle() + ", " + findBook.getAuthorName() + ", " + findBook.getGenre());
                    }
                } else if (choose.equals("2")) {
                    System.out.println("Enter Author's firstname: ");
                    String inputFirstname = scanner.nextLine();
                    System.out.println("Enter Author's lastname: ");
                    String inputLastname = scanner.nextLine();
                    List<Book> findBooks = catalog.findBooksByAuthor(inputFirstname + " " + inputLastname);
                    CatalogImpl.printBooks(findBooks);
                }
                else if(choose.equals("3")){
                    catalog.allBook(user);
                }
                else if(choose.equals("4")){
                    if(user instanceof Member){
                        CatalogImpl.printBooks(catalog.getBooks());
                        System.out.println("Choose a Book ID to loan: ");
                        int inputID = Integer.parseInt(scanner.nextLine());
                        System.out.println("Return date's book: ");
                        LocalDate inputLocalDate = LocalDate.parse(scanner.nextLine());
                        if(catalog.findBookByID(inputID).getAvailableCopies() > 0){
                            Loan loan = new Loan(inputLocalDate, (Member) user, catalog.findBookByID(inputID));
                            loanSystem.addLoan(loan);
                            loanSystem.printMyLoanBooks((Member) user);
                        }
                        else {
                            System.out.println("This books is not exist anymore, you must wait.");
                        }
                    }else {
                        System.out.println("You must a Member.");
                    }
                } else if (choose.equals("5")) {
                    if(user instanceof Member){

                        loanSystem.printMyLoanBooks((Member) user);
                    }
                } else if (choose.equals("6")) {
                    LoanSystem.checkAllExpirationDate
                            (loanSystem.getLoans(), (Member) user, LocalDate.parse("2023-08-01"));
                } else if (choose.equals("7")) {
                    exit = 1;
                }
            }
        }
    }
}