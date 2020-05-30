package com.backend.code.Repoistry;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.backend.code.Objects.displayComment;

import org.springframework.jdbc.core.RowMapper;

public class commentMapper implements RowMapper<displayComment> {

    @Override
    public displayComment mapRow(ResultSet rs, int rowNum) throws SQLException {
        displayComment dc =new displayComment();
        dc.userId=rs.getInt("userid");
        dc.comment=rs.getString("comment");
        dc.commentId=rs.getInt("commentid");
        dc.username=rs.getString("username");
        return dc;
    }

}
