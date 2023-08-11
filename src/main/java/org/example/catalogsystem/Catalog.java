package org.example.catalogsystem;

import org.example.bookmanagement.Author;
import org.example.bookmanagement.Book;

import java.util.List;

public interface Catalog {
    Book findBook(String title);
    void addBook(Book book);
    List<Book> findBooksByAuthor(String authorName);
    void addAuthor(Author author);
}
