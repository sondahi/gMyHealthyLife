package com.comert.mhl.web.filter;

import com.comert.mhl.database.common.model.dto.Authentication;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(
        filterName = "SessionFilter",
        urlPatterns = {"/login", "/logout", "/adminview/*", "/nutritionistview/*", "/userview/*"},
        dispatcherTypes = {DispatcherType.REQUEST}
)
public class SessionFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("session filter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            Authentication authentication = (Authentication) session.getAttribute("authentication");
            if (authentication != null) {
                chain.doFilter(request, response);
            } else {
                session.invalidate();
                httpServletResponse.sendRedirect("/indexview/index.xhtml");
            }
        } else
            httpServletResponse.sendRedirect("/indexview/index.xhtml");
    }
}
