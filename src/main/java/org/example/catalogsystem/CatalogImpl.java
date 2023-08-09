package org.example.catalogsystem;

import org.example.bookmanagement.Book;

import java.util.ArrayList;
import java.util.List;

public class CatalogImpl implements Catalog{
    private List<Book> books = new ArrayList<>();

    @Override
    public Book findBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public List<Book> findBooksByAuthor(String authorName) {
        List<Book> booksByAuthor = new ArrayList<>();

        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(authorName)) {
                booksByAuthor.add(book);
            }
        }

        return booksByAuthor;
    }

    public static void printBooks(List<Book> books){
        for (int i = 0; i < books.size(); i++) {
            System.out.println("Book " + i + ": " +
                    books.get(i).getTitle() + ", " + books.get(i).getAuthor() + ", " + books.get(i).getGenre());
        }
    }
}
