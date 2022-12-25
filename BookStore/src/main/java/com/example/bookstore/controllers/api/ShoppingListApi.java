package com.example.bookstore.controllers.api;

import com.example.bookstore.models.Book;
import com.example.bookstore.repository.BookRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet("/api/shopping-list")
public class ShoppingListApi extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        //noinspection unchecked
        Set<Long> shoppingList = (Set<Long>) session.getAttribute("shoppingList");
        if (shoppingList == null) {
            shoppingList = new HashSet<>();
            session.setAttribute("shoppingList", shoppingList);
        }
        resp.getWriter().write(shoppingList.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        @SuppressWarnings("unchecked")
        Set<Long> shoppingList = (Set<Long>) session.getAttribute("shoppingList");

        // If the shopping list does not exist, create a new list
        if (shoppingList == null) {
            shoppingList = new HashSet<>();
            session.setAttribute("shoppingList", shoppingList);
        }

        // Read the item to be added from the request body
        String item = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        // Check if item is valid
        try {
            long id = Long.parseLong(item);
            Optional<Book> optionalBook = BookRepository.findById(id);
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                if (book.isAvailable()) {
                    shoppingList.add(id);

                    response.setStatus(200);
                    response.setContentType("application/json");
                    response.getWriter().write(shoppingList.toString());
                } else {
                    response.setStatus(400);
                    response.getWriter().write("Book is not available");
                }
            } else {
                response.setStatus(400);
                response.getWriter().write("Book not found");
            }
        } catch (NumberFormatException e) {
            response.sendError(400);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        @SuppressWarnings("unchecked")
        Set<Long> shoppingList = (Set<Long>) session.getAttribute("shoppingList");

        // If the shopping list does not exist, create a new list
        if (shoppingList == null) {
            shoppingList = new HashSet<>();
            session.setAttribute("shoppingList", shoppingList);
        }

        // Read the item to be added from the request body
        String item = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        // Check if item is valid
        try {
            long id = Long.parseLong(item);
            Optional<Book> optionalBook = BookRepository.findById(id);
            if (optionalBook.isPresent()) {
                shoppingList.remove(id);

                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().write(shoppingList.toString());
            } else {
                resp.setStatus(404);
                resp.getWriter().write("Book not found");
            }
        } catch (NumberFormatException e) {
            resp.sendError(400);
        }
    }
}
