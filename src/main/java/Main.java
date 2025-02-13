import dao.BookDAO;
import dao.BorrowService;
import dao.UserDAO;
import models.Book;
import models.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            UserDAO userDAO = new UserDAO(connection);
            BookDAO bookDAO = new BookDAO(connection);
            BorrowService borrowService = new BorrowService(connection);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nLibrary Management System");
                System.out.println("1. Add User");
                System.out.println("2. Add Book");
                System.out.println("3. Borrow Book");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter Password: ");
                        String password = scanner.nextLine();
                        System.out.print("Enter Role (admin/student/faculty): ");
                        String role = scanner.nextLine();

                        User user = new User();
                        user.setName(name);
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setRole(role);

                        userDAO.addUser(user);
                        System.out.println("User added successfully!");
                        break;

                    case 2:
                        System.out.print("Enter Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter Author: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter Genre: ");
                        String genre = scanner.nextLine();
                        System.out.print("Enter ISBN: ");
                        String isbn = scanner.nextLine();
                        System.out.print("Enter Total Copies: ");
                        int totalCopies = scanner.nextInt();
                        scanner.nextLine();

                        Book book = new Book();
                        book.setTitle(title);
                        book.setAuthor(author);
                        book.setGenre(genre);
                        book.setIsbn(isbn);
                        book.setTotalCopies(totalCopies);
                        book.setAvailableCopies(totalCopies);

                        bookDAO.addBook(book);
                        System.out.println("Book added successfully!");
                        break;

                    case 3:
                        System.out.print("Enter User ID: ");
                        int userId = scanner.nextInt();
                        System.out.print("Enter Book ID: ");
                        int bookId = scanner.nextInt();
                        scanner.nextLine();

                        borrowService.borrowBook(userId, bookId);
                        System.out.println("Book borrowed successfully!");
                        break;

                    case 4:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
