package org.example.usermanagement;

import org.example.catalogsystem.CatalogImpl;

public class Librarian extends User{

    private static int id = 0;
    private int librarianID;
    public Librarian(String firstname, String lastname, String username, String password) {
        super(firstname, lastname, username, password);
        id = id + 1;
        this.librarianID = id;
    }

    public int getLibrarianID() {
        return librarianID;
    }

    @Override
    public boolean authenticate(String inputUsername, String inputPassword) {
        return getUsername().equals(inputUsername) && getPassword().equals(inputPassword);
    }

    @Override
    public String displayUserInfo() {
        return "Librarian{" +
                "firstname='" + getFirstname() + '\'' +
                ", lastname='" + getLastname() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", librarian id='" + getLibrarianID() + '\'' +
                '}';
    }

    public void ShowAllBooks(CatalogImpl catalog){
        catalog.allBook(this);
    }
}
