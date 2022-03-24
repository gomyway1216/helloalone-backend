package com.yudaiyaguchi.helloalonebackend.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.yudaiyaguchi.helloalonebackend.exception.AccessTokenException;

import com.yudaiyaguchi.helloalonebackend.security.services.UserDetailsServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
	  if(checkAccessTokenRequired(request.getRequestURI())) {
		  String jwt = null;
	    try {
	      LOGGER.info("request : {}", request);
	      LOGGER.info("request.getRequestURI() : {}", request.getRequestURI());
	      jwt = parseJwt(request);
	      LOGGER.info("JWT: {}", jwt);
	      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
	        String username = jwtUtils.getUserNameFromJwtToken(jwt);
	  
	        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
	            userDetails.getAuthorities());
	        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        LOGGER.info("verification success");
	      } else {
	    	  LOGGER.error("JWT is invalid : {}", jwt);
	    	  throw new AccessTokenException(jwt, "AccessToken is expired. Please try logging in");
	      }
	    } catch (Exception e) {
	    	LOGGER.error("Cannot set user authentication: {}", e);
	    	throw new AccessTokenException(jwt, "AccessToken is expired. Please try logging in");
	    }
	  }
    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7, headerAuth.length());
    }

    return null;
  }
  
  private boolean checkAccessTokenRequired(String uri) {
//	  if(uri.equals("/api/auth/refreshtoken")) {
//		  return false;
//	  } else {
//		  return true;
//	  }
	  if(uri.contains("/api/auth")) {
		  return false;
	  } else {
		  return true;
	  }
  }
}