package com.backend.code.Repoistry;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        pr.date=rs.getString("date");
        return pr;
    }

}
