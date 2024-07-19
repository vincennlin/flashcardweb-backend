package com.vincennlin.noteservice.filter;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;

// 未完成

public class FlashcardwebExceptionTranslationFilter extends ExceptionTranslationFilter {

    public FlashcardwebExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationEntryPoint);
    }

    @Override
    public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
        super.setAccessDeniedHandler(accessDeniedHandler);
    }
}
