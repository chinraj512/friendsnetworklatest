package com.backend.code.Repoistry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.backend.code.Objects.messageobj;
import com.backend.code.Objects.userProfile;

public class UserMessage implements RowMapper<messageobj> {

	@Override
	public messageobj mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		messageobj msg=new messageobj();
		msg.user1=rs.getInt("user1");
		msg. user2=rs.getInt("user2");
		msg. messagenum=rs.getInt("messagenum");
		msg. message=rs.getString("message");
		msg. createdtime=rs.getDate("createdtime");
		msg. senderid=rs.getInt("sender");
		return msg;
	}

}
