package com.comert.mhl.web.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet ( name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet ( HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException {
        logout ( request, response );
    }

    @Override
    protected void doPost ( HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException {
        logout ( request, response );
    }

    private void logout(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
        request.getSession ( false ) .invalidate ();
        request.getRequestDispatcher ( "/indexview/index.xhtml" ).forward ( request, response );
    }
}
