package com.backend.code.Repoistry;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.backend.code.Objects.UserDetails;
public class UserDetailsRowMapper implements RowMapper<UserDetails> {
@Override
public UserDetails mapRow(ResultSet rs, int arg1) throws SQLException {
UserDetails user = new UserDetails();
user.setUserid(rs.getInt("userid"));
user.setUsername(rs.getString("username"));
user.setPassword(rs.getString("password"));
user.setEmail(rs.getString("email"));
user.setPhonenumber(rs.getString("phonenumber"));;
user.setDateofbirth(rs.getString("dateofbirth"));
user.setGender(rs.getString("gender"));
user.setAge(rs.getString("age"));
return user;
}
}