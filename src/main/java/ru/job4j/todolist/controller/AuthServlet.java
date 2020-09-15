package ru.job4j.todolist.controller;

import ru.job4j.todolist.db.HbmItems;
import ru.job4j.todolist.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String email = req.getParameter("email");
       String password = req.getParameter("password");
        User user = null;
        try {
            user = HbmItems.instOf().findByEmail(email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (user != null && user.getEmail().equals(email) && user.getPassword().equals(password)) {
           HttpSession session = req.getSession();
           session.setAttribute("user", user);
           resp.sendRedirect(req.getContextPath() + "/tasks.html");
       } else {
           req.setAttribute("error", "Неверный email или password");
           req.getRequestDispatcher("index.jsp").forward(req, resp);
       }
    }
}
