package com.backend.code.Repoistry;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.backend.code.Objects.postResult;
import com.backend.code.Objects.shortProfile;

public class shortProfileMapper implements RowMapper<shortProfile>{

	@Override
	public shortProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		shortProfile sp=new shortProfile();
		sp.work=rs.getString("work");
		sp.college=rs.getString("college");
		sp.degree=rs.getString("degree");
		sp.friends=rs.getInt("friends");
		sp.locality=rs.getString("locality");
		sp.school=rs.getString("school");
		return sp;
	}

}
