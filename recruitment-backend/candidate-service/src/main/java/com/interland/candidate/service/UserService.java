package com.interland.candidate.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.interland.candidate.dto.JwtAuthenticationResponse;
import com.interland.candidate.dto.RefreshTokenRequest;
import com.interland.candidate.dto.UserDTO;

public interface UserService {


//	 UserDetails userDetails(String id);

	UserDetailsService userDetailsService();
	 JwtAuthenticationResponse login(UserDTO dto);
	 JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
