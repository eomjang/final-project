package com.chat.dto;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class ChatRoomRepository {
	//채팅방 객체 저장할 맵
	private Map<String, ChatRoomDTO> chatRoomMap;
	
	public ChatRoomRepository() {
		chatRoomMap = new HashMap<String, ChatRoomDTO>();
		for(int i=0;i<2;i++) {
			String uuid = UUID.randomUUID().toString();
			 ChatRoomDTO room = new ChatRoomDTO(uuid, "Title " + uuid, "User" + uuid, "passwd", new Date(), "region");
			chatRoomMap.put(room.getId(), room);
			System.out.println("chat room 클래스 복제중");
			System.out.println("chatroom -> " + room);
		}
	}
	
	public ChatRoomDTO getChatRoom(String id) {
		return chatRoomMap.get(id);
	}
	
	public Map<String, ChatRoomDTO> getChatRooms(){
		return chatRoomMap;
	}
	
	public void remove(WebSocketSession session) {
		getRooms().parallelStream().forEach(chatRoom -> chatRoom.remove(session));
    }
	
	//채팅방 생성하여 맵에 등록
	public String createChatRoom() {
		String uuid = UUID.randomUUID().toString();
		 ChatRoomDTO room = new ChatRoomDTO(uuid, "Title " + uuid, "User" + uuid, "passwd", new Date(), "region");
		chatRoomMap.put(room.getId(), room);
		return uuid;
	}
	
	//채팅방 목록 가져오기
	public Collection<ChatRoomDTO> getRooms(){
		return chatRoomMap.values();
	}

	public List<String> getParticipants(String roomId) {
		 ChatRoomDTO room = chatRoomMap.get(roomId);
	        if (room != null) {
	            return room.getParticipants();
	        }
	        return Collections.emptyList();
	    }
	
	//채팅방 비밀번호 조회 
}
