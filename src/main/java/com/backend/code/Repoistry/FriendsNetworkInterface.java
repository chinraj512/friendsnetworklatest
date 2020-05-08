package com.backend.code.Repoistry;

import java.util.List;

import com.backend.code.Objects.UserDetails;

public interface FriendsNetworkInterface {

	List<UserDetails> findById(int userid);
	void insertUsersDetails(UserDetails user);
	
}
