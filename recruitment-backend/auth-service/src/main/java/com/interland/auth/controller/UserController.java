package com.interland.auth.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interland.auth.dto.JwtAuthenticationResponse;
import com.interland.auth.dto.RefreshTokenRequest;
import com.interland.auth.dto.ServiceResponse;
import com.interland.auth.dto.UserDTO;
import com.interland.auth.entity.User;
import com.interland.auth.service.UserService;

@RestController
@RequestMapping("/user")

public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDTO dto)  {
		 try {
			 System.out.println("hiiii");
		        JwtAuthenticationResponse authenticationResponse = userService.login(dto);
		        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
		    } catch (DisabledException e) {
		        ServiceResponse serviceResponse = new ServiceResponse(e.getMessage(),"",null);
		        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
		    } catch (BadCredentialsException e) {
		    	ServiceResponse serviceResponse = new ServiceResponse("Incorrect password","",null);
		        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
		    } catch (UsernameNotFoundException e) {
		    	ServiceResponse serviceResponse = new ServiceResponse("Username Not Found","",null);
		    	return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
		    } catch (RuntimeException e) {
		    	ServiceResponse serviceResponse = new ServiceResponse(e.getMessage(),"",null);
		        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
		    } catch (Exception e) {
		    	ServiceResponse serviceResponse = new ServiceResponse("An error occurred during authentication","",null);
		        return new ResponseEntity<>(serviceResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}
	
	@GetMapping("/refresh")
	public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
		return new ResponseEntity<>( userService.refreshToken(refreshTokenRequest),new HttpHeaders(),HttpStatus.OK);
	}
	
	
	@GetMapping("/getCurrentUser")
	public User getCurrentUser(Principal principal){
		return ((User)this.userService.userDetailsService().loadUserByUsername(principal.getName()));
	}
	
	
	@PostMapping("/registerAdmin")
	public ResponseEntity<ServiceResponse> registerAdmin(@RequestBody UserDTO dto) {
		return new ResponseEntity<>(userService.addAdmin(dto), new HttpHeaders(), HttpStatus.OK);
	}
}
