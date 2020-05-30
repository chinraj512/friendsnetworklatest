package com.backend.code.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="likec")
public class like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="likeid",nullable=false,unique=true)
    private int likeId;
    @Column(name="postid",nullable=false)
    private int postId;
    @Column(name="userid",nullable=false)
    private int userId;
    
    
    public int getLikeId(){
        return likeId;
    }
    public void setLikeId(int likeId){
        this.likeId=likeId;
    }
    public int getPostId(){
        return postId;
    }
    public void setPostId(int postId){
        this.postId=postId;
    }
    public int getUserId(){
        return userId;
    }
    public void setUserId(int userId){
        this.userId=userId;
    }
}