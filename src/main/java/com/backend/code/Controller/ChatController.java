package com.backend.code.Controller;

import com.backend.code.Objects.ChatMessage;
import com.backend.code.Objects.chatUsers;

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
	public List <String> loginUsers=new ArrayList<String>(); 
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;	
	@MessageMapping("/sendMessage")
	@SendTo("/topic/pubic")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		return chatMessage;
	}

	@MessageMapping("/sendPrivateMessage")
	public void sendPrivateMessage(@Payload ChatMessage chatMessage) {
		System.out.println(chatMessage.getReceiver());
		simpMessagingTemplate.convertAndSendToUser(
				chatMessage.getReceiver().trim(), "/queue", chatMessage); 
	}
    
	@MessageMapping("/addUser")
	@SendTo("/topic/pubic")
	public ChatMessage addUser(@Payload ChatMessage chatMessage,
			SimpMessageHeaderAccessor headerAccessor) {
		// Add user in web socket session
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		loginUsers.add("username");
		return chatMessage;
	}
	
}