package com.backend.code.Repoistry;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.backend.code.Objects.IdName;

public class IdNameMapper implements RowMapper<IdName>{


	    @Override
	    public IdName mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	
	    	IdName id=new IdName();
	    	id.userid=rs.getInt("userid");
	    	id.username=rs.getString("username");
	        
	        return id;
	    }

	}


