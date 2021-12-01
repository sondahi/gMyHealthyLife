package com.comert.mhl.web.filter;

import com.comert.mhl.database.common.model.dto.Authentication;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebFilter(
        filterName = "UserFilter",
        urlPatterns = {"/userview/*"},
        dispatcherTypes = {DispatcherType.REQUEST}
)
public class UserFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("user filter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Authentication authentication = (Authentication) httpServletRequest.getSession(false).getAttribute("authentication");
        switch (authentication.getMemberType()) {
            case USER_TYPE_0:
            case USER_TYPE_1:
            case USER_TYPE_2:
            case USER_TYPE_3:
                chain.doFilter(request, response);
                break;
            default:
                httpServletResponse.sendRedirect("/indexview/index.xhtml");
        }
    }
}
