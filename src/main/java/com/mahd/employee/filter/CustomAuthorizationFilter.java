package com.mahd.employee.filter;

import static java.util.Arrays.stream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.interfaces.Claim;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
		log.info("Running Authorization");
		if(request.getServletPath().equals("/login") || request.getServletPath().equals("/api/token/refresh")) {
			filterChain.doFilter(request, response);
		}
		else {
			log.info("Token{}",request.getParameter("token"));
			String authorizationHeader = request.getParameter("token");
			if(authorizationHeader != null) {
				log.info("{}",authorizationHeader);
				try {
					String token = authorizationHeader;
					Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(token);
					String username = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					
					
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
					stream(roles).forEach(role -> {
						authorities.add(new SimpleGrantedAuthority(role));
					});
					
					UsernamePasswordAuthenticationToken authenticationToken =
							new UsernamePasswordAuthenticationToken(username,null,authorities);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					log.info("Security Context Holder :{}",authenticationToken);
					
					filterChain.doFilter(request, response);
				}
				catch (Exception exception) {
					log.error("Error Logging in {}",exception.getMessage());
					response.setHeader("error", exception.getMessage());
					//response.sendError(HttpStatus.FORBIDDEN.value()); 
					response.setStatus(HttpStatus.FORBIDDEN.value());
					Map<String, String> error = new HashMap<>();
					error.put("ERROR MESSAGE", exception.getMessage());
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(),error);
					
				}
			}
			else {
				filterChain.doFilter(request, response);
			}
		
		}
	}
}


