package com.backend.code.Controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.code.Objects.UserDetails;
import com.backend.code.Repoistry.FriendsNetworkRepoistry;

@RestController
@RequestMapping("/signup" )
public class Controller {
	@Autowired
	
	FriendsNetworkRepoistry repo;
	UserDetails user;
	 @GetMapping("/addprofile")
	 public String profile(@RequestBody com.backend.code.Objects.profile userprofile)
	 {
		  repo.profile(userprofile);
		  return "success";
	 }
	 @GetMapping(path="/{userid}")
	 public List<UserDetails> findById(@PathVariable ("userid") int userid )
	 {
		 return repo.findById(userid);
	 }
	 @PostMapping("/createUser")
	 public void insertUsersDetails(@RequestBody UserDetails user)
	 {
		 repo.insertUsersDetails(user);
	 }
	

}
