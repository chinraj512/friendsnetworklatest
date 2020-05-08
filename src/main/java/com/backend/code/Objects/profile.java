package com.backend.code.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="profile")
public class profile{
    @Id
    private int userId;
    private String school;
    private String college;
    private String degree;
    private String work;
    private String locality;
    
    public void setUserId(int userId)
    {
        this.userId=userId;
    }
    public int getUserId()
    {
        return userId;
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
