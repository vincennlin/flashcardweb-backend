package com.vincennlin.noteservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.noteservice.exception.ErrorDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.Date;

// 未完成

public class FlashcardwebAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        try {
            ObjectMapper mapper = new ObjectMapper();
            ErrorDetails errorDetails = new ErrorDetails(
                    new Date(), accessDeniedException.getMessage(), request.getRequestURI());
            mapper.writeValue(response.getOutputStream(), errorDetails);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}
