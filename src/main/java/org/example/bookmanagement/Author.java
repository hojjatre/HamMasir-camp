package org.example.bookmanagement;

public class Author {
    private String firstname;
    private String lastname;

    public Author(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getName() {
        return firstname + " " + lastname;
    }
}
