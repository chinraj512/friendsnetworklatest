package com.backend.code.Repoistry;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.backend.code.Objects.UserDetails;

import org.springframework.jdbc.core.RowMapper;

public class userDetailMapper implements RowMapper<UserDetails> {

    @Override
    public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserDetails user = new UserDetails()
        return null;
    }

}