package com.backend.code.Controller;

import com.backend.code.Objects.ChatMessage;
import com.backend.code.Objects.chatUsers;
import com.backend.code.Objects.ChatMessage.MessageType;
import com.backend.code.Repoistry.FriendsNetworkRepoistry;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ChatController {
	public static ArrayList <Integer> loginUsers=new ArrayList<Integer>();

	@Autowired
	FriendsNetworkRepoistry repo;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/sendMessage")
	@SendTo("/topic/pubic")
	public ChatMessage sendMessage(@Payload final ChatMessage chatMessage) {
		return chatMessage;
	}

	@MessageMapping("/sendPrivateMessage")
	public void sendPrivateMessage(@Payload final ChatMessage chatMessage) throws SQLException {
			repo.insertmessages(chatMessage);
		simpMessagingTemplate.convertAndSendToUser(
			String.valueOf(chatMessage.getReceiver()), "/queue", chatMessage);
		simpMessagingTemplate.convertAndSendToUser(
			String.valueOf(chatMessage.getSender()), "/queue", chatMessage); 
	}

	@MessageMapping("/addUser")
	@SendTo("/topic/pubic")
	public ChatMessage addUser(@Payload ChatMessage chatMessage,
			SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		loginUsers.add(chatMessage.getSender());
		return chatMessage;
	}

	
	
}