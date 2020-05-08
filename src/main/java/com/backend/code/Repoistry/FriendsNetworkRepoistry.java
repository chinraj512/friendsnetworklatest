package com.backend.code.Repoistry;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.backend.code.Objects.UserDetails;

@Repository
public class FriendsNetworkRepoistry implements FriendsNetworkInterface {

	NamedParameterJdbcTemplate template;
	public FriendsNetworkRepoistry(NamedParameterJdbcTemplate template) {  
        this.template = template;  
}  
@Override
public List<UserDetails> findAll() {
return template.query("select * from userDetails", new UserDetailsRowMapper());
}
@Override
public void insertUserDetails(UserDetails user) {
	
 final String sql = "insert into userdetails(user_id, username , email,dob,gender,age,password) values( :user_id, :username, :email, :dob, :gender, :age, :password)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
.addValue("userId", user.getUser_id())
.addValue("userName", user.getUserName())
.addValue("email", user.getEmailId())
.addValue("dob", user.getDateOfBirth())
.addValue("gender", user.getGender())
.addValue("age", user.getAge())
.addValue("password", user.getPassword());
        
        template.update(sql,param, holder);
}
	

}
