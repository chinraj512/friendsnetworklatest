package com.backend.code.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="profile")
public class profile{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userid;
    private String school;
    private String college;
    private String degree;
    private String work;
    private String locality;
    private int picid;
    public int getPicid() {
		return picid;
	}
	public void setPicId(int picid) {
		this.picid = picid;
	}
	public void setUserid(int userid)
    {
        this.userid=userid;
    }
    public int getUserid()
    {
        return userid;
    }
    public void setSchool(String school)
    {
        this.school=school;
    }
    public String getSchool()
    {
        return school;
    }
    public void setCollege(String college)
    {
        this.college=college;
    }
    public String getCollege()
    {
        return college;
    }
    public void setDegree(String degree)
    {
        this.degree=degree;
    }
    public String getDegree()
    {
        return degree;
    }
    public void setWork(String work)
    {
        this.work=work;
    }
    public String getWork()
    {
        return work;
    }
    public void setLocality(String locality)
    {
        this.locality=locality;
    }
    public String getLocality()
    {
        return locality;
    }

}
