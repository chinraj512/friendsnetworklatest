package com.backend.code.Repoistry;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.backend.code.Objects.ChatPage;
import com.backend.code.Objects.chatUsers;


public class chatUsersMapper implements RowMapper<chatUsers>{

	@Override
	public chatUsers mapRow(ResultSet rs, int rowNum) throws SQLException {
		chatUsers obj=new chatUsers();
        obj.user1=rs.getInt("user1");
        obj.user2=rs.getInt("user2");
        obj.senderid=rs.getInt("sender");
        obj.message=rs.getString("message");
        obj.time=rs.getString("createdtime");
        if(obj.user1==obj.senderid)
        {
            obj.receiverid=obj.user2;
        }
        if(obj.user2==obj.senderid)
        {
            obj.receiverid=obj.user1;
        }
		return obj;
    }
}