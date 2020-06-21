package com.backend.code.Repoistry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		return obj;
	}

}
