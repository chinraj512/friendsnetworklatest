package com.backend.code.Entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.backend.code.Entity.ImageModel;

@Entity
@Table(name="post")
@SequenceGenerator(name="seq", initialValue=1, allocationSize=100)
public class post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id 
    @Column(name = "postid")
    public int postId;
    @JoinColumn(name = "userid")
    @ManyToOne(cascade = CascadeType.ALL)
    private UserDetails userDetails;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "picid")
    private ImageModel imagemodel;
    @Column(name = "status")
    private String status;
    @Column(name = "location")
    private String location;
    @Column(name = "likecount")
    private int likeCount;
    @Column(name = "commentcount")
    private int commentCount;
    @Column(name="date")
    private String date;
    public ImageModel getImagemodel() {
		return imagemodel;
	}

	public void setImagemodel(ImageModel imagemodel) {
		this.imagemodel = imagemodel;
	}

	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserId(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public ImageModel getImageModel() {
		return imagemodel;
	}
	public void setIamgeModel(ImageModel imagemodel) {
		this.imagemodel = imagemodel;
	}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
    public int getCommentCount()
    {
        return commentCount;
    }
    public void setCommentCount(int commentCount)
    {
        this.commentCount=commentCount;
    }
}
