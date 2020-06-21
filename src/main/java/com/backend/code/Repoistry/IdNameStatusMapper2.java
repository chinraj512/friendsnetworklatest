package com.backend.code.Repoistry;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.backend.code.Objects.IdName;
import com.backend.code.Objects.IdNameStatus2;

public class IdNameStatusMapper2 implements RowMapper<IdNameStatus2>{
	 @Override
	    public IdNameStatus2 mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	
	    	IdNameStatus2 id=new IdNameStatus2();
	    	id.userid=rs.getInt("userid");
	    	id.username=rs.getString("username");
	        id.status=rs.getInt("status");
	        id.picid=rs.getInt("picid");
	        id.picbyte=rs.getBytes("picbyte");
	        return id;
	    }

}