package com.comert.mhl.web.filter;

import com.comert.mhl.database.common.model.dto.Authentication;
import com.comert.mhl.database.member.model.entity.MemberType;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(
        filterName = "NutritionistFilter",
        urlPatterns = {"/nutritionistview/*"},
        dispatcherTypes = {DispatcherType.REQUEST}
)
public class NutritionistFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("nutritionist filter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Authentication authentication = (Authentication) httpServletRequest.getSession(false).getAttribute("authentication");
        if(authentication!=null && authentication.getMemberType() == MemberType.NUTRITIONIST)
            chain.doFilter(request, response);
        else {
            httpServletRequest.getSession(false).invalidate();
            httpServletResponse.sendRedirect("/indexview/index.xhtml");
        }
    }
}
