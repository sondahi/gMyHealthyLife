package com.comert.mhl.web.filter;

import com.comert.mhl.database.common.model.dto.Authentication;
import com.comert.mhl.database.member.model.entity.MemberType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebFilter(
        filterName = "AdminFilter",
        urlPatterns = {"/adminview/*"},
        dispatcherTypes = {DispatcherType.REQUEST}
)
public class AdminFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("admin filter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Authentication authentication = (Authentication) httpServletRequest.getSession(false).getAttribute("authentication");
        if (authentication != null && authentication.getMemberType() == MemberType.ADMIN)
            chain.doFilter(request, response);
        else {
            httpServletRequest.getSession(false).invalidate();
            httpServletResponse.sendRedirect("/indexview/index.xhtml");
        }
    }
}
