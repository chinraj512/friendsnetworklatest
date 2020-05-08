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
	System.out.println(id);
    return template.query("select * from userdetails where userid=:id", new UserDetailsRowMapper());
}

public void insertUsersDetails(@RequestBody UserDetails user)
{
	final String sql = "INSERT INTO userdetails( username, password, email, phonenumber, dateofbirth, gender, age)VALUES (:username ,:password , :email, :phonenumber, :dateofbirth, :gender, :age);";
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param = new MapSqlParameterSource()
.addValue("username", user.getUsername())
.addValue("password", user.getPassword())
.addValue("email",user.getEmail())
.addValue("phonenumber", user.getPhonenumber())
.addValue("dateofbirth", user.getDateofbirth())
.addValue("gender", user.getGender())
.addValue("age", user.getAge());
    
    template.update(sql,param, holder);

}
}
