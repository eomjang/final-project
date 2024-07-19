package com.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.chat.dto.ChatHandler;
//웹소켓 설정 부분
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
	
	private ChatHandler chatHandler;
	
	public WebSocketConfig(ChatHandler chatHandler) {
		this.chatHandler = chatHandler;
	}

	//채팅 메세지 처리할 ChatHandler와 메세지를 받을 경로 설정
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(chatHandler, "/ws/multiRoom");
	}
	
	

}
