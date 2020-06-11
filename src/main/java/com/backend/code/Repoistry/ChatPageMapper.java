package com.backend.code.Repoistry;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.backend.code.Objects.ChatPage;


public class ChatPageMapper implements RowMapper<ChatPage>{

	@Override
	public ChatPage mapRow(ResultSet rs, int rowNum) throws SQLException {
		ChatPage obj=new ChatPage();
		obj.userid=rs.getInt("userid");
		obj.message=rs.getString("message");
		obj.messagenum=rs.getInt("messagenum");
		obj.sender=rs.getInt("sender");
		obj.username=rs.getString("username");
		obj.picid=rs.getInt("picid");
		obj.picbyte=rs.getBytes("picbyte");
		obj.name=rs.getString("name");
		obj.type=rs.getString("type");
		obj.createdtime=rs.getString("createdtime");
		return obj;
	}

}
