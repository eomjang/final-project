package com.chat.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.chat.dto.ChatRoomDTO;
import com.chat.dto.ChatRoomRepository;

@Controller
public class ChatController {
	
	private ChatRoomRepository chatRoomRepository;
	
	public ChatController(ChatRoomRepository chatRoomRepository) {
		this.chatRoomRepository = chatRoomRepository;
	}

	@GetMapping("/")
	public ModelAndView main(ModelAndView view) {
		view.addObject("collection", chatRoomRepository.getRooms());
		view.setViewName("index");
		return view;
	}
	
	@GetMapping("/room")
    public ModelAndView roomController(ModelAndView view, String id) {
        view.setViewName("room");
        view.addObject("roomId", id);
        return view;
    }
	
	@RestController
	@RequestMapping("/room")
	public class ChatRoomController {
	    @Autowired
	    private ChatRoomRepository chatRoomRepository;

	    @GetMapping("/{id}/participants")
	    public ResponseEntity<List<String>> getParticipants(@PathVariable("id") String roomId) {
	        List<String> participants = chatRoomRepository.getParticipants(roomId);
	        return ResponseEntity.ok(participants);
	    }
	}
	
	//채팅방 생성
	@GetMapping("/room/new")
	public String newRoom() {
		String id = chatRoomRepository.createChatRoom();
		return "redirect:/room?id="+id;
	}
	
}
