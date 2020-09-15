package ru.job4j.todolist.controller;

import ru.job4j.todolist.db.HbmItems;
import ru.job4j.todolist.model.Item;
import ru.job4j.todolist.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/addTask")
public class AddTasksServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("text");
        Item item = new Item(text);
        item.setCreated(new Date(System.currentTimeMillis()));
        HbmItems.instOf().add(item, (User) getServletContext().getAttribute("currentUser"));
    }
}
