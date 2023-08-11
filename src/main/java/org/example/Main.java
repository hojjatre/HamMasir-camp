package org.example;

import org.example.bookmanagement.Author;
import org.example.bookmanagement.Book;
import org.example.bookmanagement.Genre;
import org.example.catalogsystem.CatalogImpl;
import org.example.usermanagement.Librarian;
import org.example.usermanagement.Member;
import org.example.usermanagement.User;
import org.example.usermanagement.UserSystem;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        UserSystem userSystem = CreateData.createMember();
        CatalogImpl catalog = CreateData.createBook();
        User user = null;

        System.out.println("You are a Member(1) or Librarian(2): ");
        Scanner scanner = new Scanner(System.in);
        int memberORlibrarian = Integer.parseInt(scanner.nextLine());

        System.out.println("Sign in / Sing up:1/2: ");
        String signInORsignUp = scanner.nextLine();

        Security security = new Security(userSystem, scanner);

        if (signInORsignUp.equals("1")){
            user = security.signIn();
        }
        else {
            user = security.signUp(memberORlibrarian);
        }
        if(security.authenticated){
            System.out.println("Search by title(1) ,Search by Author(2), All Books(3): ");
            String choose = scanner.nextLine();
            if(choose.equals("1")){
                System.out.println("Enter book's name: ");
                String name = scanner.nextLine();
                Book findBook = catalog.findBook(name);
                if(findBook != null){
                    System.out.println(findBook.getTitle() + ", " + findBook.getAuthorName() + ", " + findBook.getGenre());
                }
            } else if (choose.equals("2")) {
                System.out.println("Enter Author's firstname: ");
                String inputFirstname = scanner.nextLine();
                System.out.println("Enter Author's lastname: ");
                String inputLastname = scanner.nextLine();
                List<Book> findBooks = catalog.findBooksByAuthor(inputFirstname + " " + inputLastname);
                CatalogImpl.printBooks(findBooks);
            }
            else if(choose.equals("3")){
                catalog.allBook(user);
            }
        }


    }
}