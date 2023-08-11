package org.example.bookmanagement;

public class Book {
    private String title;
    private Author author;
    private Genre genre;

    public Book(String title, Author author, Genre genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public Book(String title, String firstname, String lastname, Genre genre){
        this.title = title;
        this.author = new Author(firstname, lastname);
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return author.getName();
    }

    public Author getAuthor(){
        return author;
    }

    public Genre getGenre() {
        return genre;
    }
}
