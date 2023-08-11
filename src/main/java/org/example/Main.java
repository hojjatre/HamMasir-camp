package org.example;

import org.example.bookmanagement.Author;
import org.example.bookmanagement.Book;
import org.example.bookmanagement.Genre;
import org.example.catalogsystem.CatalogImpl;
import org.example.usermanagement.Librarian;
import org.example.usermanagement.Member;
import org.example.usermanagement.UserSystem;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        UserSystem userSystem = CreateData.createMember();
        CatalogImpl catalog = CreateData.createBook();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Sign in / Sing up:1/2: ");
        Boolean inSystem = false;
        String str = scanner.nextLine();
        if(str.equals("1")){
            System.out.println("Username: ");
            String inputUsername = scanner.nextLine();
            System.out.println("Password: ");
            String inputPassword = scanner.nextLine();
            if (userSystem.isUserInSystem(inputUsername, inputPassword)) {
                System.out.println(userSystem.getUserType(inputUsername) + " is in the system.");
                inSystem = true;
            } else {
                System.out.println("Invalid credentials.");
            }
        } else if (str.equals("2")) {
            System.out.println("Firstname: ");
            String inputFirstname = scanner.nextLine();
            System.out.println("Lastname: ");
            String inputLastname = scanner.nextLine();
            System.out.println("Username: ");
            String inputUsername = scanner.nextLine();
            System.out.println("Password: ");
            String inputPassword = scanner.nextLine();
            Member inputMember = new
                    Member(inputFirstname, inputLastname, inputUsername, inputPassword);
            inSystem = true;
        }
        if(inSystem){
            System.out.println("Search by title(1) , by Author(2): ");
            String choose = scanner.nextLine();
            if(choose.equals("1")){
                System.out.println("Enter book's name: ");
                String name = scanner.nextLine();
                Book findBook = catalog.findBook(name);
                if(findBook != null){
                    System.out.println(findBook.getTitle() + ", " + findBook.getAuthor() + ", " + findBook.getGenre());
                }
            } else if (choose.equals("2")) {
                System.out.println("Enter Author's firstname: ");
                String inputFirstname = scanner.nextLine();
                System.out.println("Enter Author's lastname: ");
                String inputLastname = scanner.nextLine();
                List<Book> findBooks = catalog.findBooksByAuthor(inputFirstname + " " + inputLastname);
                CatalogImpl.printBooks(findBooks);
            }
        }


    }
}