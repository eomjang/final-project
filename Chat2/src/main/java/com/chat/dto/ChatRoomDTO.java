package com.chat.dto;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatRoomDTO {
    private String id;
    private String roomTitle; 
    private String userId; 
    private String passwd; 
    private Date regDate; 
    private String region;
    private boolean secretChk;
    private List<String> participants;
  
	private Set<WebSocketSession> sessions = new HashSet<>();

    // 생성자 업데이트
    public ChatRoomDTO(String room_id, String roomTitle, String userId, String passwd, Date regDate, String region) { 
        this.id = room_id;
        this.roomTitle = roomTitle;
        this.userId = userId;
        this.passwd = passwd;
        this.regDate = regDate;
        this.region = region;
    }
   
    // TYPE이 JOIN이면 해당 채팅방에 들어온 새 유저
    public void handleMessage(WebSocketSession session, ChatMessage chatMessage, ObjectMapper objectMapper) throws JsonProcessingException {
        if (chatMessage.getType().equals("JOIN")) {
            join(session);
        }
        send(chatMessage, objectMapper);
    }
 
    // 해당 채팅방에 새 멤버 등록(세션 관리)
    private void join(WebSocketSession session) {
        sessions.add(session);
    }
    
    // 채팅방에 있는 모든 멤버에 메시지 전송
    private <T> void send(T messageObject, ObjectMapper objectMapper) throws JsonProcessingException {
        TextMessage message = new TextMessage(objectMapper.writeValueAsString(messageObject));
  
        sessions.parallelStream().forEach(session -> {
            try {
                session.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
    public void remove(WebSocketSession target) {
        String targetId = target.getId();
        sessions.removeIf(session -> session.getId().equals(targetId));
    }
 
    public String getId() {
        return id;
    }
 
    public Set<WebSocketSession> getSessions() {
        return sessions;
    }

    // 새로운 필드에 대한 getter 및 setter 추가
    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    public List<String> getParticipants() {
  		return participants;
  	}

  	public void setParticipants(List<String> participants) {
  		this.participants = participants;
  	}


    @Override
    public String toString() {
        return "ChatRoom [id=" + id + ", sessions=" + sessions + ", roomTitle=" + roomTitle + ", userId=" + userId 
                + ", passwd=" + passwd + ", regDate=" + regDate + ", region=" + region + "]";
    }
}
