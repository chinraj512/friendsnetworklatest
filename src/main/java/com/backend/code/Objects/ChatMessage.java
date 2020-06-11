package com.backend.code.Objects;

import java.time.LocalDateTime;


public class ChatMessage {
    private MessageType type;
    private String content;
    private int sender;
    private int receiver;
	private LocalDateTime dateTime=LocalDateTime.now();; 
    
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
    
    public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

}