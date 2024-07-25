package com.vincennlin.userservice.config;

import com.vincennlin.userservice.filter.AuthenticationFilter;
import com.vincennlin.userservice.filter.AuthorizationFilter;
import com.vincennlin.userservice.service.UserService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
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
@Configuration
@EnableWebSecurity
public class WebSecurity {

    private Environment environment;

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http,
                                            AuthenticationManager authenticationManager) throws Exception{

        AuthenticationFilter authenticationFilter =
                new AuthenticationFilter(userService, environment, authenticationManager);
        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));

        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                // Allow all origins
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("*"));
                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                config.setAllowedHeaders(Collections.singletonList("*"));
                return config;
            }
        }));

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

        http.addFilter(authenticationFilter)
                .addFilter(new AuthorizationFilter(authenticationManager, environment));

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.sameOrigin()));

        return http.build();
    }
}
