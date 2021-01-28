package app.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} 
		catch (AlgorithmMismatchException algoMismatchEx) {

			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.getWriter().write("{ \"error\": \"" + "Authentication Failed! " + algoMismatchEx.getMessage() + "\" }");
			
		}
		catch (SignatureVerificationException signatureVerificationEx) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.getWriter().write("{ \"error\": \"" + "Authentication Failed! " + signatureVerificationEx.getMessage() + "\" }");
		}
		catch (TokenExpiredException tokenExpiredEx) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.getWriter().write("{ \"error\": \"" + "Authentication Failed! " + tokenExpiredEx.getMessage() + "\" }");
		}
		catch (JWTVerificationException jwtVerificationEx) {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.getWriter().write("{ \"error\": \"" + "Authentication Failed! " + jwtVerificationEx.getMessage() + "\" }");

		}


	}

}
