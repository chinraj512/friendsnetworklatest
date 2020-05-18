package com.backend.code.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Imagemodel")
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picid", nullable = false, unique = true)
    private int picid;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "picbyte", nullable = false)
    private byte[] picByte;

    public void setPicId(int picid) {
        this.picid = picid;
    }

    public int getPicid() {
        return picid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }

    public byte[] getPicByte() {
        return picByte;
    }
}