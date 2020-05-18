package com.backend.code.Repoistry;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.backend.code.Entity.ImageModel;

import org.springframework.jdbc.core.RowMapper;

public class ImageMapper implements RowMapper<ImageModel> {
    @Override
    public ImageModel mapRow(ResultSet rs,int arg1) throws SQLException{
        ImageModel img =new ImageModel();
        img.setName(rs.getString("name"));
        img.setType(rs.getString("type"));
        img.setPicByte(rs.getBytes("picbyte"));
        return img;
    }
}