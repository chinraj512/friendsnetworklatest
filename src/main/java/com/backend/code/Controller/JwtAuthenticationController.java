package com.backend.code.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.backend.code.service.JwtUserDetailsService;


import com.backend.code.config.JwtTokenUtil;
import com.backend.code.Objects.tokenResponse;
import com.backend.code.Objects.userpass;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody userpass authenticationRequest) throws Exception {
        try{
			authenticate(authenticationRequest.email, authenticationRequest.password);	
		}
		catch(BadCredentialsException e)
		{
		     return ResponseEntity.ok("not authenticated"); 
		}		

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.email);

		final String token = jwtTokenUtil.generateToken(userDetails);
		   System.out.println("ffrge");
				return ResponseEntity.ok(new tokenResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
}