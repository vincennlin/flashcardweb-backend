package com.vincennlin.noteservice.config;

import com.vincennlin.noteservice.filter.AuthorizationFilter;
import com.vincennlin.noteservice.filter.FlashcardwebExceptionTranslationFilter;
import com.vincennlin.noteservice.security.FlashcardwebAccessDeniedHandler;
import com.vincennlin.noteservice.security.JwtAuthenticationEntryPoint;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@SecurityScheme(
        name = "Bear Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@AllArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class WebSecurity {

    private final Environment environment;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http,
                                            AuthenticationManager authenticationManager) throws Exception{

        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(corsCustomizer -> corsCustomizer.disable());
//        http.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
//            @Override
//            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                // Allow all origins
//                CorsConfiguration config = new CorsConfiguration();
//                config.setAllowedOrigins(Collections.singletonList("*"));
//                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//                config.setAllowedHeaders(Collections.singletonList("*"));
//                return config;
//            }
//        }));

        String webExpressionString =
                "hasIpAddress('" + environment.getProperty("gateway.ip1") + "') " +
                "or hasIpAddress('" + environment.getProperty("gateway.ip2") + "')";

        http.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/api/v1/**")).access(
//                                new WebExpressionAuthorizationManager(webExpressionString))
                        .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
        );

        // 未完成，目前不會用到
        ExceptionTranslationFilter flashcardwebExceptionTranslationFilter =
                new FlashcardwebExceptionTranslationFilter(new JwtAuthenticationEntryPoint());
        flashcardwebExceptionTranslationFilter.setAccessDeniedHandler(new FlashcardwebAccessDeniedHandler());

        http.addFilter(new AuthorizationFilter(authenticationManager, environment))
                .addFilter(flashcardwebExceptionTranslationFilter)
        ;

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.sameOrigin()));

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
