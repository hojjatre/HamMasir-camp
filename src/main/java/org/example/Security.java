package org.example;

import org.example.usermanagement.Librarian;
import org.example.usermanagement.Member;
import org.example.usermanagement.User;
import org.example.usermanagement.UserSystem;

import java.util.Scanner;

public class Security {
    public boolean authenticated = false;
    private UserSystem userSystem;
    private Scanner scanner;
    public Security(UserSystem userSystem, Scanner scanner){
        this.userSystem = userSystem;
        this.scanner = scanner;
    }
    public User signIn(){
        System.out.println("Username: ");
        String inputUsername = scanner.nextLine();
        System.out.println("Password: ");
        String inputPassword = scanner.nextLine();
        if (userSystem.isUserInSystem(inputUsername, inputPassword)) {
            System.out.println(userSystem.getUserType(inputUsername) + " is in the system.");
            authenticated = true;
            return userSystem.getUser(inputUsername);
        } else {
            System.out.println("Invalid credentials.");
            authenticated = false;
            return null;
        }
    }
    public User signUp(int memberORlibrarian){
        System.out.println("Firstname: ");
        String inputFirstname = scanner.nextLine();
        System.out.println("Lastname: ");
        String inputLastname = scanner.nextLine();
        System.out.println("Username: ");
        String inputUsername = scanner.nextLine();
        System.out.println("Password: ");
        String inputPassword = scanner.nextLine();
        if(memberORlibrarian == 1){
            authenticated = true;
            return new
                    Member(inputFirstname, inputLastname, inputUsername, inputPassword);
        } else {
            authenticated = true;
            return new
                    Librarian(inputFirstname, inputLastname, inputLastname, inputPassword);
        }
    }
}
