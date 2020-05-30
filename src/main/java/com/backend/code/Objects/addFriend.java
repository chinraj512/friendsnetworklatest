package com.backend.code.Objects;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "friendsrelation")
public class addFriend{
	@Column(name="user1" ,nullable=false)
    private int user1;
    private int relation;
    private int lastAction;

    public void setUser1(int user1)
    {
         this.user1=user1;
    }
    public int getUser1()
    {
         return user1;
    }
    public void setRelation(int relation)
    {
         this.relation=relation;
    }
    public int getRelation()
    {
         return relation;
    }
    public void setLastAction(int lastAction)
    {
         this.lastAction=lastAction;
    }
    public int getLastAction()
    {
         return lastAction;
    }
    
}