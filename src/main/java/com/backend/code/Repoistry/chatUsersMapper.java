package com.backend.code.Repoistry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        String date=rs.getString("createdtime");
      	Date now=new Date();
      	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
      	String today=df.format(now); 
      	String postdate=date.substring(0,10);
        int sub1=Integer.parseInt(postdate.substring(0,2));
        int sub2=Integer.parseInt(today.substring(0,2));
        System.out.println(postdate+" "+today);
        if(postdate.equals(today))
        	obj.messageDate="Today";
        else if((sub1+1)==sub2)
        	obj.messageDate="Yesterday";
        else
          	obj.messageDate=date;
      	
        obj.messageTime=date.substring(11);
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