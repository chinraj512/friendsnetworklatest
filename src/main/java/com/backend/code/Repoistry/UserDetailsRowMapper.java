package com.backend.code.Repoistry;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.backend.code.Objects.userProfile;
public class UserDetailsRowMapper implements RowMapper<userProfile> {
@Override
public userProfile mapRow(ResultSet rs, int arg1) throws SQLException {
userProfile pro =new userProfile();
pro.userid=rs.getInt("userid");
pro.college=rs.getString("college");
pro.school=rs.getString("school");
pro.work=rs.getString("work");
pro.locality=rs.getString("locality");
pro.username=rs.getString("username");
pro.email=rs.getString("email");
pro.gender=rs.getString("gender");
pro.phonenumber=rs.getString("phonenumber");
pro.password=rs.getString("password");
pro.age=rs.getString("age");
pro.dateofbirth=rs.getString("dateofbirth");
pro.degree=rs.getString("degree");
return pro;
}
}
