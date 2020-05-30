package com.backend.code.Repoistry;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.backend.code.Objects.userpass;
public class userpassMapper implements RowMapper<userpass> {
@Override
public userpass mapRow(ResultSet rs, int arg1) throws SQLException {
userpass pro =new userpass();
pro.email=rs.getString("email");
pro.password=rs.getString("password");
pro.userid=rs.getInt("userid");
return pro;
}
}