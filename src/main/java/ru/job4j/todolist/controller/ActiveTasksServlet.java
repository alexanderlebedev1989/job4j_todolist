package ru.job4j.todolist.controller;

import com.google.gson.Gson;
import ru.job4j.todolist.db.HbmItems;
import ru.job4j.todolist.model.Item;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/activeTasks")
public class ActiveTasksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Item> items = HbmItems.instOf().select(false);
        String json = new Gson().toJson(items);
        PrintWriter pw = new PrintWriter(resp.getOutputStream(), true, StandardCharsets.UTF_8);
        pw.println(json);
    }
}
