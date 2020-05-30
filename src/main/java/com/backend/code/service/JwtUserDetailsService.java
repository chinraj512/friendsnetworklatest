package com.backend.code.service;

import java.util.ArrayList;
import java.util.List;

import com.backend.code.Objects.userpass;
import com.backend.code.Repoistry.FriendsNetworkRepoistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	FriendsNetworkRepoistry fr;
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<userpass> t = fr.findpassword(username);
			return new User(t.get(0).email,t.get(0).password ,
					new ArrayList<>());
		}
	}
