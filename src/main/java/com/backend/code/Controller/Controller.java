package com.backend.code.Controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.code.Objects.UserDetails;
import com.backend.code.Repoistry.FriendsNetworkRepoistry;

@RestController
@RequestMapping(value="/signup" )
public class Controller {
	@Resource
	@Autowired
	FriendsNetworkRepoistry repo;
	
	 @GetMapping("/enf")
	 public String Hello()
	 {
		 return "HEllo World";
	 }
	 @GetMapping("/AllUsersDetails")
	 public List<UserDetails> findAll()
	 {
		 return repo.findAll();
	 }
	 @PostMapping("/createUser")
	 public void createUser(@RequestBody UserDetails user)
	 {
		repo.insertUserDetails(user); 
	 }
}
