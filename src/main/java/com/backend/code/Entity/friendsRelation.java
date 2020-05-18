package com.backend.code.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="friendsrelation")
public class friendsRelation{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id",nullable=false,unique=true)
    private int id;
    @Column(name="user1",nullable=false)
    private int me;
    @Column(name="user2",nullable=false)
    private int friend;
    @Column(name="activity",nullable=false)
    private int activity;
    @Column(name="lastuser",nullable=false)
    private int lastUser;

    public int getMe(){
      return me;
    }
    public void setMe(int me){
        this.me=me;
    }
    public int getFriend(){
        return friend;
    }
    public void setFriend(int friend){
          this.friend=friend;
    }
    public int getActivity(){
        return activity;
    }
    public void setActivity(int activity){
          this.activity=activity;
    }
    public int getLastUser(){
        return lastUser;
    }
    public void setLastUser(int lastUser){
          this.lastUser=lastUser;
    }
}