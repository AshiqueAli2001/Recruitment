package com.interland.candidate.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.interland.candidate.dto.JwtAuthenticationResponse;
import com.interland.candidate.dto.RefreshTokenRequest;
import com.interland.candidate.dto.UserDTO;
import com.interland.candidate.entity.User;
import com.interland.candidate.repository.UserRepository;

@Service
public class UserServiceImplement implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtService jwtService;
	
	@Override
	public JwtAuthenticationResponse login(UserDTO dto) {

			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUserId(), 
						dto.getPassword()));
				
				User user = userRepository.findById(dto.getUserId())
						.orElseThrow(()-> new UsernameNotFoundException("User not found"));
				
				if (!user.isEnabled()) {
				        throw new DisabledException("User account is disabled");
				    }
							
				var jwt =jwtService.generateToken(user);
				var refreshToken =jwtService.generateRefreshToken(new HashMap<>(),user);
				
				JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
				jwtAuthenticationResponse.setToken(jwt);
				jwtAuthenticationResponse.setRefreshToken(refreshToken);
					
				return jwtAuthenticationResponse;
			}  catch (DisabledException e) {
		        throw e;
		    } catch (BadCredentialsException e) {
		        throw e;
		    } catch (UsernameNotFoundException e) {
		        throw new RuntimeException("User not found", e);
		    } catch (Exception e) {
		        throw new RuntimeException("An error occurred during authentication", e);
		    }

	}

	public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		String userId = jwtService.extractUsername(refreshTokenRequest.getToken());
		User user = userRepository.findById(userId).orElseThrow();
		
		if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
			var jwt =jwtService.generateToken(user);
			
			JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
			
			jwtAuthenticationResponse.setToken(jwt);
			jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
			return jwtAuthenticationResponse;
		}
		return null;
	}


	@Override
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username){
				return userRepository.findById(username)
						.orElseThrow(()->new UsernameNotFoundException("User Not Found"));
			}
		};
	}


	
	

}
