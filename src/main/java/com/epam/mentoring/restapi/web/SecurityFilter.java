package com.epam.mentoring.restapi.web;

import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by pengfrancis on 16/5/18.
 */

//@WebFilter(filterName="myFilter",urlPatterns="/api/*")
public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("Filtering........");
        String token= null;
        Map params= servletRequest.getParameterMap();
        try{
            token= ((String[])params.get("token"))[0];
        }catch (Exception e){
            token= null;
        }
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        System.out.println("Authenticate: "+ request.getMethod()+" "+ uri + ", token="+ token);

        boolean authorizatePass= (token!=null && token.startsWith("a"));
        // AuthorizationManager.authorize(uri, request.getMethod(), token)

        if(!authorizatePass){
            //throw new UnauthorizedError();
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            //httpResponse.sendRedirect("/login.jsp");
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpResponse.getWriter().print(HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
