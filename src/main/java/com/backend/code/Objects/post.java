package com.backend.code.Objects;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name="post")
@SequenceGenerator(name="seq", initialValue=1, allocationSize=100)
public class post {
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    @Id int id;
    private int postId;
    private int userId;
    private int picId;
    private String status;
    private String location;
    private int likeCount;
    private int commentCount;
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
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