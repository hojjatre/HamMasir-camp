package org.example.usermanagement;

public class Librarian extends User{

    private int librarianID;
    public Librarian(String firstname, String lastname, String username, String password, int librarianID) {
        super(firstname, lastname, username, password);
        this.librarianID = librarianID;
    }

    public int getLibrarianID() {
        return librarianID;
    }

}
