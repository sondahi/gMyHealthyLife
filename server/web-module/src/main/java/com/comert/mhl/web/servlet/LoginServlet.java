package com.comert.mhl.web.servlet;

import com.comert.mhl.database.common.model.dto.Authentication;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Authentication authentication = (Authentication) session.getAttribute("authentication");
        switch (authentication.getMemberType()) {
            case USER_TYPE_0:
            case USER_TYPE_1:
            case USER_TYPE_2:
            case USER_TYPE_3:
                request.getRequestDispatcher("/userview/home.xhtml").forward(request, response);
                break;
            case NUTRITIONIST:
                request.getRequestDispatcher("/nutritionistview/home.xhtml").forward(request, response);
                break;
        }
    }
}
