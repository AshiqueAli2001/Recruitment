package com.interland.auth.service;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.interland.auth.dto.Constants;
import com.interland.auth.dto.JwtAuthenticationResponse;
import com.interland.auth.dto.RefreshTokenRequest;
import com.interland.auth.dto.ServiceResponse;
import com.interland.auth.dto.UserDTO;
import com.interland.auth.entity.Role;
import com.interland.auth.entity.User;
import com.interland.auth.exception.RecordCreateException;
import com.interland.auth.repository.UserRepository;

@Service
public class UserServiceImplement implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtService jwtService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	MessageSource messageSource;
	
	 private static Logger logger =LogManager.getLogger(UserServiceImplement.class);
	
	@Override
	public JwtAuthenticationResponse login(UserDTO dto) {

			try {
				User user = userRepository.findById(dto.getUserId())
						.orElseThrow(()-> new UsernameNotFoundException("User not found"));
				if(user.getStatus().equals(Constants.MESSAGE_STATUS.PROCESSED)) {
					throw new DisabledException("User account is disabled");
				}
				if(user.getStatus().equals(Constants.MESSAGE_STATUS.DELETE)||user.getStatus().equals(Constants.MESSAGE_STATUS.DELETED)) {
					throw new DisabledException("User account is deleted");
				}
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUserId(), 
						dto.getPassword()));
				
				
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
		        throw e;
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

	
	public ServiceResponse addAdmin(UserDTO dto) {
	    try {
	        User user = new User();
	        if (userRepository.existsById(dto.getUserId())) {
	            throw new RecordCreateException("admin.details.psh.VAL0010");
	        }

	        BeanUtils.copyProperties(dto, user);
			user.setPassword(passwordEncoder.encode(dto.getUserId()) );
			user.setRole(Role.ADMIN);
			user.setHighestSCore(0);
			user.setNoOfAttempts(0);
			
			user.setCreatedBy("DEFAULT");
			user.setCreatedTime(new Date());
			user.setStatus(Constants.MESSAGE_STATUS.VERIFIED);
			user.setModifiededBy("DEFAULT");
			userRepository.save(user);	        
	        return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
	                messageSource.getMessage("admin.details.psh.VAL0001", null, LocaleContextHolder.getLocale()), null);
	    } catch (RecordCreateException e) {
	        logger.error("Error:" + e.getMessage(), e);
	        return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
	                messageSource.getMessage("admin.details.psh.VAL0010", null, LocaleContextHolder.getLocale()), null);
	    } catch (Exception e) {
	        logger.error("Error:" + e.getMessage(), e);
	        return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
	                messageSource.getMessage("admin.details.psh.VAL0002", null, LocaleContextHolder.getLocale()), null);
	    }
	}
	

	
	

}
