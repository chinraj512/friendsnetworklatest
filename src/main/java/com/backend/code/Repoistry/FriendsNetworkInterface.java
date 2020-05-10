package com.backend.code.Repoistry;

import java.util.List;

import com.backend.code.Objects.UserDetails;
import com.backend.code.Objects.userProfile;

public interface FriendsNetworkInterface {

	List<userProfile> findById(int userid);
	void insertUsersDetails(UserDetails user);
	
}
