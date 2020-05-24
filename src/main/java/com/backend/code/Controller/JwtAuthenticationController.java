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

import java.util.List;

import com.backend.code.Objects.tokenResponse;
import com.backend.code.Objects.userpass;
import com.backend.code.Repoistry.FriendsNetworkRepoistry;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private FriendsNetworkRepoistry repo;
	@Autowired
	private JwtUserDetailsService userDetailsService;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody userpass authenticationRequest) throws Exception {
	
		try {
			authenticate(authenticationRequest.email, authenticationRequest.password);
		} catch (BadCredentialsException e) {
			return ResponseEntity.ok().body("not Authenticated");
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.email);
		List<userpass> uid=repo.findpassword(authenticationRequest.email); 
		String token = jwtTokenUtil.generateToken(userDetails);
		token=token+" "+Integer.toString(uid.get(0).userid);
		return ResponseEntity.ok().body(new tokenResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
}