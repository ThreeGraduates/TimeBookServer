//package com.filter;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
///**
// * 实现过滤器
// */
//@WebFilter(filterName = "loginFilter",urlPatterns = "/*")
//public class LoginFilter implements Filter {
//    private final Logger LOGGER= LoggerFactory.getLogger(LoginFilter.class);
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        LOGGER.info("过滤器被创建。。。。");
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
//        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
//        HttpSession session=httpRequest.getSession();
//        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
//        LOGGER.info("url:{}",url);
//        if(url.indexOf("/toLogin")>-1||url.indexOf("/register")>-1||url.indexOf(".css")>-1||url.indexOf(".js")>-1||session.getAttribute("username")!=null){
//            filterChain.doFilter(httpRequest, httpResponse);
//        }else{
//            httpResponse.sendRedirect("/admin/toLogin");
//        }
//    }
//
//    @Override
//    public void destroy() {
//    }
//}
