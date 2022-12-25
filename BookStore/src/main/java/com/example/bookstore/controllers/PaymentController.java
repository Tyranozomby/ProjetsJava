package com.example.bookstore.controllers;

import com.example.bookstore.repository.BookRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@WebServlet("/payment")
public class PaymentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        //noinspection unchecked
        Set<Long> shoppingList = (Set<Long>) session.getAttribute("shoppingList");

        if (shoppingList == null) {
            shoppingList = new HashSet<>();
            session.setAttribute("shoppingList", shoppingList);
        }

        request.setAttribute("shoppingList", new ArrayList<>(shoppingList.stream()
                .map(id -> BookRepository.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .toList()));

        request.getRequestDispatcher("/WEB-INF/jsp/payment.jsp").forward(request, response);
    }
}
