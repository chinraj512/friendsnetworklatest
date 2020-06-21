package com.backend.code.Repoistry;
     
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.backend.code.Objects.messageobj;
import com.backend.code.Objects.userProfile;

public class UserMessage implements RowMapper<messageobj> {

	@Override
	public messageobj mapRow(ResultSet rs, int rowNum) throws SQLException {
		messageobj msg=new messageobj();
		msg.user1=rs.getInt("user1");
		msg. user2=rs.getInt("user2");
		msg. messagenum=rs.getInt("messagenum");
		msg. message=rs.getString("message");
		String date=rs.getString("createdtime");
		Date now=new Date();
      	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
      	String today=df.format(now); 
      	String postdate=date.substring(0,10);
        int sub1=Integer.parseInt(postdate.substring(0,2));
        int sub2=Integer.parseInt(today.substring(0,2));
        System.out.println(postdate+" "+today);
        if(postdate.equals(today))
        	msg.messageDate="Today";
        else if((sub1+1)==sub2)
        	msg.messageDate="Yesterday";
        else
          	msg.messageDate=date;
        msg.messageTime=date.substring(11);
		msg. senderid=rs.getInt("sender");
		return msg;
	}

}
