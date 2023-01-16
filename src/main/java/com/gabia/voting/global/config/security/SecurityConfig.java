package com.gabia.voting.global.config.security;

import com.gabia.voting.client.type.ClientType;
import com.gabia.voting.global.config.filter.JwtAuthenticationFilter;
import com.gabia.voting.global.config.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .headers().frameOptions().disable()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
            .and()
                .authorizeRequests((auth) ->
                        auth
                                .antMatchers(HttpMethod.POST, "/api/v0/item/**").hasRole(ClientType.ROLE_MANAGER.getRole())
                                .antMatchers(HttpMethod.DELETE, "/api/v0/item/**").hasRole(ClientType.ROLE_MANAGER.getRole())
                                .antMatchers(HttpMethod.PUT, "/api/v0/item/**").hasRole(ClientType.ROLE_MANAGER.getRole())
                                .antMatchers(HttpMethod.POST, "/api/v0/vote/*/client/*").hasRole(ClientType.ROLE_SHAREHOLDER.getRole())
                                .antMatchers(HttpMethod.GET, "/api/v0/**").hasRole(ClientType.ROLE_USER.getRole())
                                .anyRequest().permitAll()
                );

        http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
                .antMatchers(
                        "/h2-console/**"
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
