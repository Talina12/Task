package com.food4good.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.food4good.database.entities.User;
import com.food4good.database.repositories.UsersRepository;
import com.google.common.base.Strings;

public class SuperAdminFilter extends GenericFilterBean{
UsersRepository usersRepository;
	
	public SuperAdminFilter(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("in SuperAdminFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (!isSkipMethod(httpRequest)) {
        	if (isCheckedMethod(httpRequest)) {
        	String authorizationHeader = httpRequest.getHeader("Authorization");
        	String token = authorizationHeader.split("Bearer ")[1];
            if (Strings.isNullOrEmpty(token)) {
            	setException(httpServletResponse);
                return;
            }
            Optional<User> userOptional = usersRepository.findByTokenAndRoles(token, "SUPER_ADMIN");
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
        List<String> listOfFreeUri = Arrays.asList("swagger", "login", "api-docs","error");
        boolean match = listOfFreeUri.stream().anyMatch(s -> uri.contains(s));
        return match;
    }
    
    private boolean isCheckedMethod(HttpServletRequest httpRequest) {
    	String uri = httpRequest.getRequestURI();
    	if (uri.contains("superAdmin")) return true;
    	else return false;
    }
}


