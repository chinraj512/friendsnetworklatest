package com.backend.code.Repoistry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.backend.code.Objects.postResult;

import org.springframework.jdbc.core.RowMapper;

public class postMapper implements RowMapper<postResult> {

    @Override
    public postResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        postResult pr =new postResult();
        pr.userId=rs.getInt("userid");
        pr.postId=rs.getInt("postid");
        pr.name=rs.getString("name");
        pr.type=rs.getString("type");
        pr.picByte=rs.getBytes("picbyte");
        pr.userName=rs.getString("username");
        pr.status=rs.getString("status");
        pr.location=rs.getString("location");
        pr.likeCount=rs.getInt("likecount");
        pr.commentCount=rs.getInt("commentcount");
        String date=rs.getString("date");
      	Date now=new Date();
      	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
      	String today=df.format(now); 
      	String postdate=date.substring(0,10);
        int sub1=Integer.parseInt(postdate.substring(0,2));
        int sub2=Integer.parseInt(today.substring(0,2));
        System.out.println(postdate+" "+today);
        if(postdate.equals(today))
        	pr.postDate="Today";
        else if((sub1+1)==sub2)
        	pr.postDate="Yesterday";
        else
          	pr.postDate=date;
      	
        pr.postTime=date.substring(11);
        pr.liked=rs.getBoolean("liked");
        return pr;
    }

}