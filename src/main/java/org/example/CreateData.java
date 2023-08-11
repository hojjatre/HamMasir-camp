package org.example;

import org.example.bookmanagement.Author;
import org.example.bookmanagement.Book;
import org.example.bookmanagement.Genre;
import org.example.catalogsystem.CatalogImpl;
import org.example.usermanagement.Librarian;
import org.example.usermanagement.Member;
import org.example.usermanagement.UserSystem;

public class CreateData {
    public static UserSystem createMember(){
        UserSystem userSystem = new UserSystem();

        Member member1 = new
                Member("Ali", "Moradi", "AliM", "alim");
        userSystem.addUser(member1);
        Member member2 = new
                Member("Hojjat", "Rezaei", "HojjatRE", "hojjatre");
        userSystem.addUser(member2);
        Member member3 = new
                Member("Reza", "Hasani", "RezaH021", "rezah021");
        userSystem.addUser(member3);

        Librarian librarian = new
                Librarian("Ahmad", "Radad", "AhmadRa", "ahmadra");
        userSystem.addUser(librarian);

        return userSystem;
    }

    public static CatalogImpl createBook(){
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

        return catalog;
    }
}
