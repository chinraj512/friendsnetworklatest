package com.backend.code.Entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.backend.code.Entity.ImageModel;

@Entity
@Table(name = "profile")
public class profile implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6837855030712905254L;
    @Column(name = "school")
    private String school;
    @Column(name = "college")
    private String college;
    @Column(name = "degree")
    private String degree;
    @Column(name = "work")
    private String work;
    @Column(name = "locality")
    private String locality;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "picid")
    private ImageModel imagemodel;
    @Id
    @JoinColumn(name = "userid",nullable=false,unique=true)
    @OneToOne(cascade = CascadeType.ALL)
    private UserDetails userDetails;
    public ImageModel getImageModel() {
		return imagemodel;
	}
	public void setIamgeModel(ImageModel imagemodel) {
		this.imagemodel = imagemodel;
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
    public void setUserDetails(UserDetails userDetails)
    {
        this.userDetails=userDetails;
    }
    public UserDetails getUserDetails()
    {
        return userDetails;
    }

}
