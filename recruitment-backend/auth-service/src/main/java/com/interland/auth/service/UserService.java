package com.interland.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.interland.auth.dto.JwtAuthenticationResponse;
import com.interland.auth.dto.RefreshTokenRequest;
import com.interland.auth.dto.ServiceResponse;
import com.interland.auth.dto.UserDTO;

public interface UserService {


//	 UserDetails userDetails(String id);

	UserDetailsService userDetailsService();
	 JwtAuthenticationResponse login(UserDTO dto);
	 JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
	 ServiceResponse addAdmin(UserDTO dto);
}
