package com.backend.code.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="comment")
public class comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="commentid",nullable=false,unique=true)
    private int commentId;
    @Column(name="postid",nullable=false)
    private int postId;
    @Column(name="userid",nullable=false)
    private int userId;
    @Column(name="comment",nullable=false)
    private String comment;

    public int getCommentId(){
        return commentId;
    }
    public void setCommentId(int commentId){
        this.commentId=commentId;
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
    public String getComment(){
        return comment;
    }
    public void setComment(String comment){
        this.comment=comment;
    }

    
    
    
}