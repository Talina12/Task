package com.food4good.security;

import com.food4good.database.entities.User;
import com.food4good.database.repositories.UsersRepository;
import com.google.common.base.Strings;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TokenCheckFilter extends GenericFilterBean {

    UsersRepository usersRepository;

    public TokenCheckFilter(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("in TokenCheckFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if(httpRequest.getMethod().equals(HttpMethod.OPTIONS.name()))
        {
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
            httpServletResponse.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,Authorization");
        }else {
            if (!isSkipMethod(httpRequest)) {
                String authorizationHeader = httpRequest.getHeader("Authorization");
                if (Strings.isNullOrEmpty(authorizationHeader)) {
                    setException(httpServletResponse);
                    return;
                }
                String token = authorizationHeader.split("Bearer ")[1];
                if (Strings.isNullOrEmpty(token)) {
                    setException(httpServletResponse);
                    return;
                }
                Optional<User> userOptional = usersRepository.findByTokenAndRoles(token, "USER");
                if (!userOptional.isPresent()) {
                    setException(httpServletResponse);
                    return;
                }
                User user = userOptional.get();
                Authentication authentication = new UsernamePasswordAuthenticationToken(user, token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }



    private void setException(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write("Authetication failed");
        writer.flush();
    }

    private boolean isSkipMethod(HttpServletRequest httpRequest) {
        String uri = httpRequest.getRequestURI();
        List<String> listOfFreeUri = Arrays.asList("swagger", "login", "api-docs","error", "admin");
        boolean match = listOfFreeUri.stream().anyMatch(s -> uri.contains(s));
        return match;
    }
}