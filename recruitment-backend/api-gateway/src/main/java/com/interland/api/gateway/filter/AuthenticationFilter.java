package com.interland.api.gateway.filter;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import io.jsonwebtoken.Claims;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator validator;
	@Autowired
	private JwtUtil jwtUtil;

	public AuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			try {
				if (validator.isSecured.test(exchange.getRequest())) {
					if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
						throw new RuntimeException("missing authorization header");

					}

					String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
					if (authHeader != null && authHeader.startsWith("Bearer ")) {
						authHeader = authHeader.substring(7);
					}

					jwtUtil.validateToken(authHeader);
					
					
					Claims claims = jwtUtil.extractAllClaims(authHeader);
					List<String> roles = (List<String>) claims.get("roles");
					String requestPath = exchange.getRequest().getURI().getPath();
					
					if (requestPath.startsWith("/admin/") && hasRole(roles, "ADMIN")) {
						return chain.filter(exchange);
					} else if (requestPath.startsWith("/candidate/") && hasRole(roles, "USER")) {
						return chain.filter(exchange);
					} else {
						throw new RuntimeException("Invalid request or insufficient roles");
					}

				}
			}

			catch (RuntimeException e) {
                throw new RuntimeException(e.getMessage());
            }

			catch (Exception e) {
				throw new RuntimeException(e.getMessage());

			}

			return chain.filter(exchange);
		});
	}

	private boolean hasRole(List<String> roles, String targetRole) {
		return roles != null && roles.contains(targetRole);
	}

	public static class Config {

	}

}
