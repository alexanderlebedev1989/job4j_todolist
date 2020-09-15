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

@WebServlet("/reg")
public class RegServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = new User(name, email, password);
        HttpSession session = req.getSession();
        HbmItems.instOf().addUser(user);
        session.setAttribute("user", user);
        resp.sendRedirect(req.getContextPath() + "/");

    }
}
