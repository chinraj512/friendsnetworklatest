package com.backend.code.Objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;


public class ChatMessage {
    private MessageType type;
    private String content;
    private int sender;
    private int receiver;
	Date present = new Date();
	private String messageDate=new SimpleDateFormat("dd/MM/yyyy HH:mm aa").format(present);
    private String messageTime;
    public String getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}

	public String getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(String messageTime) {
		this.messageTime = messageTime;
	}

	public enum MessageType {
        CHAT,
        JOIN,
        LEAVE,
        TYPING
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }
    
    public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}


}