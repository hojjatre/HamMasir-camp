package org.example;

import org.example.bookmanagement.Author;
import org.example.bookmanagement.Book;
import org.example.bookmanagement.Genre;
import org.example.catalogsystem.CatalogImpl;
import org.example.usermanagement.Librarian;
import org.example.usermanagement.Member;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Member member1 = new
                Member("Ali", "Moradi", "AliM", "alim", 1);
        Member member2 = new
                Member("Hojjat", "Rezaei", "HojjatRE", "hojjatre", 2);

        Member member3 = new
                Member("Reza", "Hasani", "RezaH021", "rezah021", 3);


        Librarian librarian = new
                Librarian("Ahmad", "Radad", "AhmadRa", "ahmadra", 1);


        Author author1 = new Author("JRR", "Tolkien");
        Author author2 = new Author("Dan", "Brown");
        Author author3 = new Author("JK", "Rowling");

        CatalogImpl catalog = new CatalogImpl();

        Book book1 = new Book("The Hobbit", author1, Genre.FANTASY);
        catalog.addBook(book1);
        Book book2 = new Book("The Da Vinci Code", author2, Genre.MYSTERY);
        catalog.addBook(book2);
        Book book3 = new Book("Harry Potter and the Chamber of Secrets", author3, Genre.FANTASY);
        catalog.addBook(book3);
        Book book4 = new Book("Harry Potter and the Philosopher's Stone", author3, Genre.FANTASY);
        catalog.addBook(book4);


//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Sign in / Sing up:1/2: ");
//        String str = scanner.next();
//        if(str.equals("1")){
//            System.out.println("Username: ");
//            String username = scanner.next();
//            System.out.println("Password: ");
//            String password = scanner.next();
//
//        }
//        System.out.println("Search by title(1) , by Author(2): ");

    }
}