package org.example.catalogsystem;

import org.example.bookmanagement.Author;
import org.example.bookmanagement.Book;
import org.example.usermanagement.Member;
import org.example.usermanagement.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CatalogImpl implements Catalog{
    private List<Book> books = new ArrayList<>();
    private HashMap<Author, List<Book>> data = new HashMap<Author, List<Book>>();

    @Override
    public Book findBookByName(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    @Override
    public Book findBookByID(int id) {
        for (Book book : books) {
            if (book.getID() == id) {
                return book;
            }
        }
        return null;
    }

    @Override
    public void addBook(Book book) {
        if (data.containsKey(book.getAuthor())) {
            data.get(book.getAuthor()).add(book);
        }
        else {
            addAuthor(book.getAuthor());
            data.get(book.getAuthor()).add(book);
        }
        books.add(book);
    }

    @Override
    public List<Book> findBooksByAuthor(String authorName) {
        for (Author author:data.keySet()) {
            if(author.getName().equals(authorName)){
                return data.get(author);
            }
        }
        return null;
    }

    @Override
    public void addAuthor(Author author){
        data.put(author, new ArrayList<Book>());
    }

    @Override
    public void allBook(User user){
        if (user instanceof Member) {
            System.out.println("You cannot access this option.");
        }
        else {
            for (Author author:data.keySet()) {
                System.out.println("Author: " + author.getName());
                printBooks(data.get(author));
            }
        }
    }

    public static boolean printBooks(List<Book> books){
        for (int i = 0; i < books.size(); i++) {
            System.out.println("Book " + books.get(i).getID() + ": " +
                    books.get(i).getTitle() + ", " + books.get(i).getAuthorName() + ", " + books.get(i).getGenre() +
                    ", " + books.get(i).getAvailableCopies());
        }
        return false;
    }

    public List<Book> getBooks() {
        return books;
    }
}
