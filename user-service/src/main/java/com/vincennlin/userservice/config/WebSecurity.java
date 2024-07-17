package com.vincennlin.userservice.config;

import com.vincennlin.userservice.security.AuthenticationFilter;
import com.vincennlin.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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

@AllArgsConstructor
@Configuration("userServiceWebSecurity")
@EnableWebSecurity
public class WebSecurity {

    private Environment environment;

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Bean("userServiceAuthenticationManager")
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }

    @Bean("userServiceSecurityFilterChain")
    protected SecurityFilterChain configure(HttpSecurity http,
                                            @Qualifier("userServiceAuthenticationManager") AuthenticationManager authenticationManager) throws Exception{

        AuthenticationFilter authenticationFilter =
                new AuthenticationFilter(userService, environment, authenticationManager);
        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));

        http.csrf(AbstractHttpConfigurer::disable);

        String webExpressionString =
                "hasIpAddress('" + environment.getProperty("gateway.ip1") + "') " +
                "or hasIpAddress('" + environment.getProperty("gateway.ip2") + "')";

        http.authorizeHttpRequests(auth ->
                auth
//                        .requestMatchers(new AntPathRequestMatcher("/api/v1/**")).permitAll()
//                        .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/actuator/**")).access(
                                new WebExpressionAuthorizationManager(webExpressionString)
                        )
                        .requestMatchers(new AntPathRequestMatcher("/api/v1/**")).access(
                                new WebExpressionAuthorizationManager(webExpressionString))
//                        .anyRequest().authenticated()
        );

        http.addFilter(authenticationFilter);

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.headers((headers) -> headers.frameOptions((frameOptions) -> frameOptions.sameOrigin()));

        return http.build();
    }
}
