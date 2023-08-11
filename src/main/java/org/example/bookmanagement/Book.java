package org.example.bookmanagement;

public class Book {
    private static int staticID = 0;
    private int ID;
    private String title;
    private Author author;
    private Genre genre;
    private int copies;
    private int availableCopies;

    public Book(String title, Author author, Genre genre, int copies, int availableCopies) {
        staticID = staticID + 1;
        this.ID = staticID;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.copies = copies;
        this.availableCopies = availableCopies;
    }

    public Book(String title, String firstname, String lastname, Genre genre, int copies, int availableCopies){
        staticID = staticID + 1;
        this.title = title;
        this.author = new Author(firstname, lastname);
        this.genre = genre;
        this.copies = copies;
        this.availableCopies = availableCopies;
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

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public int getID() {
        return ID;
    }
}
