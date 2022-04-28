package com.ncedu.nc_edu.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    @Configuration
    public static class ApiSecurityAdapter extends WebSecurityConfigurerAdapter {
        @Bean
        public AccessDeniedHandler accessDeniedHandler() {
            return new AccessDeniedHandlerImpl();
        }

        @Bean
        public AuthenticationEntryPoint authenticationEntryPoint() {
            return new AuthenticationEntryPointImpl();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new SCryptPasswordEncoder();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/admin/**").access("@securityAccessResolverImpl.isAdmin()")
                    .antMatchers("/moderator/**").access("@securityAccessResolverImpl.isModerator()")
                    .antMatchers("/users").access("@securityAccessResolverImpl.isAdminOrModerator()")
                    .antMatchers("/users/{userId}/reviews").access("@securityAccessResolverImpl.isSelfOrGranted(#userId)")
                    .antMatchers(HttpMethod.PATCH, "/users/{userId}").access("@securityAccessResolverImpl.isSelf(#userId)")
                    .antMatchers(HttpMethod.PUT, "/recipes/{recipeId}/**").access("@securityAccessResolverImpl.isRecipeOwnerOrGranted(#recipeId)")
                    .antMatchers(HttpMethod.DELETE, "/recipes/{recipeId}/**").access("@securityAccessResolverImpl.isRecipeOwnerOrGranted(#recipeId)")
                    .antMatchers(HttpMethod.POST, "/recipes/{recipeId}/reviews").access("!@securityAccessResolverImpl.isRecipeOwner(#recipeId)")
                    .antMatchers(HttpMethod.PUT, "/reviews/{reviewId}").access("@securityAccessResolverImpl.isReviewOwner(#reviewId)")
                    .antMatchers(HttpMethod.DELETE, "/reviews/{reviewId}").access("@securityAccessResolverImpl.isReviewOwnerOrGranted(#reviewId)")
                    .antMatchers(HttpMethod.POST, "/ingredients/**").access("@securityAccessResolverImpl.isAdminOrModerator()")
                    .antMatchers(HttpMethod.PUT, "/ingredients/**").access("@securityAccessResolverImpl.isAdminOrModerator()")
                    .antMatchers(HttpMethod.DELETE, "/ingredients/**").access("@securityAccessResolverImpl.isAdminOrModerator()")
                    .antMatchers(HttpMethod.GET, "/recipes/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/reviews/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/pictures/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/ingredients/**").permitAll()
                    .antMatchers("/").permitAll()
                    .antMatchers("/register").permitAll()
                    .anyRequest().authenticated()
            .and()
                    .httpBasic()
            .and()
                    .exceptionHandling()
                        .accessDeniedHandler(accessDeniedHandler())
                        .authenticationEntryPoint(authenticationEntryPoint())
            .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                    .cors();
        }
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }


}
