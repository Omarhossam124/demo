//package com.bm.transfer.authentication.config;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.web.session.InvalidSessionStrategy;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class CustomSessionExpirationHandler implements InvalidSessionStrategy {
//
//    @Override
//    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.getWriter().write("Session expired. Please log in again.");
//        response.getWriter().flush();
//    }
//}
