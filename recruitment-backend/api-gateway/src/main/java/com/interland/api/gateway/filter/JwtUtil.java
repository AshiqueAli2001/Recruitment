package com.interland.api.gateway.filter;

import java.security.Key;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {
	
	public static final String SECRET = "413F4428472B4B6250655368566D5970337336763979244226452948404D6351";
	

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignKey() {
		byte[] key = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(key);
	}
	
	public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }
	


}
