package com.example.demo.config;

import java.util.Base64;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.example.demo.model.User;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.service.UserService;

import jakarta.annotation.PostConstruct;

@Component
public class UserAuthenticationProvider {

	@Value("${security.jwt.token.secret-key:secret-value}")
	private String secretKey;
	
	@Autowired
	private UserService userService;
	
	public UserAuthenticationProvider(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@PostConstruct
	protected void init() {
		secretKey=Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	public String createToken(String email) {
	    Date now = new Date();
	    Date validity = new Date(now.getTime() + 3600000); // 1 hour expiration
	    Algorithm algorithm = Algorithm.HMAC256(secretKey);
	    
	    return JWT.create()
	       .withIssuer(email)
	       .withIssuedAt(now)
	       .withExpiresAt(validity)
	       .sign(algorithm);
	}

	
	public Authentication validateToken (String token) {
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
		DecodedJWT decoded = verifier.verify(token);
		User user = userService.findByEmail(decoded.getSubject());
		
		return new UsernamePasswordAuthenticationToken(user,null,Collections.EMPTY_LIST);


	}
}
