package com.backend.code.Repoistry;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.backend.code.Objects.ImageModel;

import org.springframework.jdbc.core.RowMapper;

public class ImageMapper implements RowMapper<ImageModel> {
    @Override
    public ImageModel mapRow(ResultSet rs,int arg1) throws SQLException{
        ImageModel img =new ImageModel(rs.getString("name"),rs.getString("type"),rs.getBytes("picbyte"));
        return img;
    }
}