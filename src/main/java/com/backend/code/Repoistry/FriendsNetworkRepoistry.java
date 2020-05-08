package com.backend.code.Repoistry;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;


import com.backend.code.Objects.UserDetails;

@Repository
public class FriendsNetworkRepoistry implements FriendsNetworkInterface {

	NamedParameterJdbcTemplate template;
	public FriendsNetworkRepoistry(NamedParameterJdbcTemplate template) {  
        this.template = template;  
}  
@Override
public List<UserDetails> findById(int id) {
return template.query("select * from userdetails where userid=id", new UserDetailsRowMapper());
}

public void insertUsersDetails(@RequestBody UserDetails user)
{
	final String sql = "INSERT INTO userdetails(userid, username, password, email, phonenumber, dateofbirth, gender, age)VALUES (:userid, :username ,:password , :email, :phonenumber, :dateofbirth, :gender, :age);";
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param = new MapSqlParameterSource()
.addValue("userid",user.getUserid())
.addValue("username", user.getUsername())
.addValue("password", user.getPassword())
.addValue("email",user.getEmail())
.addValue("phonenumber", user.getPhonenumber())
.addValue("dateofbirth", user.getDateofbirth())
.addValue("gender", user.getGender())
.addValue("age", user.getAge());
    
    template.update(sql,param, holder);

}
public void profile(com.backend.code.Objects.profile userprofile) {
        
        final String sql = "INSERT INTO profile(user_id, school, college, degree,work, locality)VALUES (:userid, :school ,:college , :degree, :work, :locality);";
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param = new MapSqlParameterSource()
.addValue("userid",userprofile.getUserId())
.addValue("school", userprofile.getSchool())
.addValue("college", userprofile.getCollege())
.addValue("degree", userprofile.getDegree())
.addValue("work",userprofile.getWork())
.addValue("locality", userprofile.getLocality());
    
    template.update(sql,param, holder); 
}
}
