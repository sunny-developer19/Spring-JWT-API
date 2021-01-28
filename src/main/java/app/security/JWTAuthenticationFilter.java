package app.security;


import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.core.userdetails.User;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import app.dto.JWTtoken;
import app.dto.RefreshToken;
import app.dto.UserDetails;
import app.security.SecurityConstants;
import app.services.RefreshTokenService;
import app.services.RefreshTokenServiceImpl;
import app.utils.BeanUtil;
import app.utils.RefreshTokenGeneratorUtil;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
   
    
    private RefreshTokenService refreshTokenService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = BeanUtil.getBean(RefreshTokenServiceImpl.class);
  
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/access-tokens", "POST"));
        
         
 
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
        	
        	
        	
        	UserDetails creds = new ObjectMapper()
                    .readValue(req.getInputStream(), UserDetails.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
            
          
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(HMAC512(SecurityConstants.SECRET.getBytes()));
        
        
        RefreshToken refreshToken =  refreshTokenService.getByUserEmail(((User) auth.getPrincipal()).getUsername());
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JWTtoken tok = new JWTtoken();
        tok.setJwt(token);
        tok.setRefresh_token(refreshToken.getToken());
        
        PrintWriter out = res.getWriter();
        out.print(gson.toJson(tok));
        out.flush();
      
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
       
       
    	response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getOutputStream().println("{ \"error\": \"" + "Authentication Failed! User email or password not valid." + "\" }");

    }
	
}