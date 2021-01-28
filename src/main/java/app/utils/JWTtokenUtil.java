package app.utils;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import app.dto.UserDetails;
import app.security.SecurityConstants;

public class JWTtokenUtil {

	
	public static String getJWTtokenFromUser(UserDetails user)
	{
		String token = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(HMAC512(SecurityConstants.SECRET.getBytes()));

		return token;
	}
	
	public static String getJWTtokenFromUserEmail(String userEmail)
	{
		String token = JWT.create()
                .withSubject(userEmail)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(HMAC512(SecurityConstants.SECRET.getBytes()));

		return token;
	}
	
	public static String getUserFromToken(String jwtTokenStr) {
        if (jwtTokenStr != null) {
            // parse the token.
            String userEmail = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()))
                    .build()
                    .verify(jwtTokenStr.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getSubject();

            if (userEmail != null) {
               return userEmail;
            }
            return null;
        }
        return null;
	}
}
