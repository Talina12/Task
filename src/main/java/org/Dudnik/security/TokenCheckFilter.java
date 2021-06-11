package org.Dudnik.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.Dudnik.config.GlobalProperties;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@Slf4j
@WebFilter("/loginapi/user/get_name")
@Component
public class TokenCheckFilter  implements Filter {

    private JWTVerifier jwtVerifier;
    private Algorithm algorithm;
    private GlobalProperties globalProperties;

    public TokenCheckFilter(GlobalProperties globalProperties){
        this.globalProperties = globalProperties;
        this.algorithm = algorithm = Algorithm.HMAC256(globalProperties.getSecret());
        this.jwtVerifier = JWT.require(algorithm).withIssuer("auth0").build();
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("in TokenCheckFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String authorizationHeader = httpRequest.getHeader("Authorization");
        if (Strings.isEmpty(authorizationHeader) || Objects.isNull(authorizationHeader)) {
            setException(httpServletResponse, "Authetication failed");
            return;
        }
        try {
            DecodedJWT decodedToken = jwtVerifier.verify(authorizationHeader);
            httpRequest.setAttribute("decodedToken", decodedToken);// instead of saving the user in the SecurityContextHolder
        }
        catch (JWTVerificationException exception){
            setException(httpServletResponse, "the token is not valid");
            return;
        }
        chain.doFilter(request, response);
    }



    public void setException(HttpServletResponse httpServletResponse, String message) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write(message);
        writer.flush();
    }


}
