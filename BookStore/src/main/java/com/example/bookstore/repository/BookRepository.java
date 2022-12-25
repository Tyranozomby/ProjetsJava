package com.example.bookstore.repository;

import com.example.bookstore.models.Book;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BookRepository {

    private static final int UPDATE_INTERVAL = 15 * 60 * 1000; // 15 minutes

    private static DataSource dataSource;

    private static Date lastUpdate;

    private static List<Book> books;

    static {
        try {
            // Load the JDBC driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create the DataSource object
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setServerName("rogeaux.com");
            dataSource.setPort(3306);
            dataSource.setDatabaseName("devoirs");
            dataSource.setUser("devoirs");
            dataSource.setPassword("SuperMotDePasseDeLaMortQuiTue42"); // Woula, c'est un mot de passe de ouf!

            BookRepository.dataSource = dataSource;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Init books and lastUpdate
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM jee_bookstore ORDER BY title");
            books = getBooks(resultSet);
            lastUpdate = new Date();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Book> findAll() {
        return queryBooks();
    }

    public static Optional<Book> findById(long id) {
        return queryBooks().stream().filter(book -> book.getId() == id).findFirst();
    }

    public static List<Book> findByContent(String search) {
        String lower_search = search.toLowerCase();
        return queryBooks()
                .stream()
                .filter(b -> b.getTitle().toLowerCase().contains(lower_search) ||
                        b.getDescription().toLowerCase().contains(lower_search) ||
                        b.getAuthor().toLowerCase().contains(lower_search) ||
                        b.getPublisher().toLowerCase().contains(lower_search) ||
                        b.getGenre().toLowerCase().contains(lower_search)
                ).toList();
    }

    private static List<Book> queryBooks() {
        if (new Date().getTime() - lastUpdate.getTime() < UPDATE_INTERVAL) {
            return books;
        }

        try (Connection conn = dataSource.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM jee_BookStore ORDER BY title");

            lastUpdate = new Date();
            return books = getBooks(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Book> getBooks(ResultSet rs) throws SQLException {
        List<Book> books = new ArrayList<>();
        while (rs.next()) {
            books.add(getBook(rs));
        }
        return books;
    }

    private static Book getBook(ResultSet rs) throws SQLException {
        return new Book(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("publisher"),
                rs.getDate("publication_date"),
                rs.getString("genre"),
                rs.getString("description"),
                rs.getInt("page_count"),
                rs.getString("cover_art"),
                rs.getBigDecimal("price"),
                rs.getBoolean("available")
        );
    }
}
