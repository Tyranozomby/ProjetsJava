package com.example.bookstore.controllers;

import com.example.bookstore.models.Book;
import com.example.bookstore.repository.BookRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/books")
public class BookController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // If there is an id parameter, we are looking for a specific book
        String id = request.getParameter("bookId");
        if (id != null) {
            Optional<Book> book = BookRepository.findById(Long.parseLong(id));
            if (book.isPresent()) {
                request.setAttribute("book", book.get());
                request.getRequestDispatcher("/WEB-INF/jsp/book.jsp").forward(request, response);
            } else {
                response.sendError(404);
            }
        } else {
            // Otherwise, we are looking for a list of books
            String search = request.getParameter("search");
            request.setAttribute("search", search);
            List<Book> books;
            if (search != null) {
                books = BookRepository.findByContent(search);
            } else {
                books = BookRepository.findAll();
            }
            request.setAttribute("books", books);
            request.getRequestDispatcher("/WEB-INF/jsp/books_list.jsp").forward(request, response);
        }
    }
}
