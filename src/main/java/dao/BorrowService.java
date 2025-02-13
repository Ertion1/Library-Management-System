package dao;

import models.Book;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

public class BorrowService {
    private Connection connection;

    public BorrowService(Connection connection) {
        this.connection = connection;
    }

    public void borrowBook(int userId, int bookId) throws SQLException {
        BookDAO bookDAO = new BookDAO(connection);
        Book book = bookDAO.getBookById(bookId);

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("models.Book not available!");
        }

        String sql = "INSERT INTO borrow_records (user_id, book_id, borrow_date, due_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);

            Date borrowDate = new Date(System.currentTimeMillis());
            stmt.setDate(3, borrowDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(borrowDate);
            calendar.add(Calendar.DAY_OF_MONTH, 14); // 2 weeks due date
            stmt.setDate(4, new Date(calendar.getTimeInMillis()));

            stmt.executeUpdate();

            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookDAO.addBook(book);
        }
    }

}
