package com.food4good.security;

import com.food4good.database.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class CustomWebSecurityConfigurerAdapter
        extends WebSecurityConfigurerAdapter {
    @Autowired
    UsersRepository usersRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry auth = http.csrf().disable().authorizeRequests();

        auth.antMatchers("/v2/api-docs", "/swagger-resources/**", "/csrf","/login").permitAll();
        http.addFilterBefore(
                new TokenCheckFilter(usersRepository), BasicAuthenticationFilter.class);
    }
}