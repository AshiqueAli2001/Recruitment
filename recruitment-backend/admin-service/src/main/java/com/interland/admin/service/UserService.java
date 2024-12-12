package com.interland.admin.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.interland.admin.dto.JwtAuthenticationResponse;
import com.interland.admin.dto.RefreshTokenRequest;
import com.interland.admin.dto.UserDTO;

public interface UserService {


//	 UserDetails userDetails(String id);

	UserDetailsService userDetailsService();
	 JwtAuthenticationResponse login(UserDTO dto);
	 JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
