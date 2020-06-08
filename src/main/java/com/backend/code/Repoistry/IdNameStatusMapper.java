package com.backend.code.Repoistry;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.backend.code.Objects.IdName;
import com.backend.code.Objects.IdNameStatus;

public class IdNameStatusMapper implements RowMapper<IdNameStatus>{
	 @Override
	    public IdNameStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	
	    	IdNameStatus id=new IdNameStatus();
	    	id.userid=rs.getInt("userid");
	    	id.username=rs.getString("username");
	        id.status=rs.getBoolean("status");
	        return id;
	    }

}
