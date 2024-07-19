package com.chat.dto;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class ChatHandler extends TextWebSocketHandler{
	private ObjectMapper objectMapper;
	//채팅방 관리하는 클래스
	private ChatRoomRepository repository;
	
	public ChatHandler(ObjectMapper objectMapper, ChatRoomRepository repository) {
		this.objectMapper = objectMapper;
		this.repository = repository;
	}

	//사용자가 보낸 메세지를 받는 부분
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
        System.out.println("Received payload: " + payload);
		ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        System.out.println("Parsed ChatMessage: " + chatMessage.getMessage());
        //채팅방 아이디로 채팅방 정보 가져온 후 해당 메세지 전송
		ChatRoomDTO chatRom = repository.getChatRoom(chatMessage.getChatRoomId());
		if(chatRom == null) {
			System.out.println("아이디를 찾을 수 없습니다" + chatMessage.getChatRoomId());
		}
		chatRom.handleMessage(session, chatMessage, objectMapper);
		
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		repository.remove(session);
	}
	
	
}
