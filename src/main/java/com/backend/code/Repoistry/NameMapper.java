package com.backend.code.Repoistry;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class NameMapper implements RowMapper<Integer> {

    @Override
    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
    	
     
    	int id=rs.getInt("picid");
        
        return id;
    }

}
