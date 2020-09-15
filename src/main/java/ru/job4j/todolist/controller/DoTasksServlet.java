package ru.job4j.todolist.controller;

import ru.job4j.todolist.db.HbmItems;
import ru.job4j.todolist.model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/doTask")
public class DoTasksServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       int id = Integer.parseInt(req.getParameter("id"));
       Item item = HbmItems.instOf().findById(id);
       item.setDone(true);
       HbmItems.instOf().replace(id, item);
    }
}
