package com.backend.code.Repoistry;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.backend.code.Objects.UserDetails;
public class UserDetailsRowMapper implements RowMapper<UserDetails> {
@Override
public UserDetails mapRow(ResultSet rs, int arg1) throws SQLException {
UserDetails user = new UserDetails();
user.setUserId(rs.getInt("userid"));
user.setUserName(rs.getString("username"));
user.setEmailId(rs.getString("email"));
user.setAge(rs.getString("age"));
user.setPhoneNumber(rs.getString("phonenumber"));
user.setPassword(rs.getString("password"));
user.setDateOfBirth(rs.getString("dateofbirth"));
user.setGender(rs.getString("genders"));
return user;
}
}