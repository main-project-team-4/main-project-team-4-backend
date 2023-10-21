package com.example.demo.chat.controller;

import com.example.demo.chat.dto.ChatRoomRequestDto;
import com.example.demo.chat.dto.ChatRoomResponseDto;
import com.example.demo.chat.service.ChatRoomService;
import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.chat.entity.LoginInfo;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.member.entity.Member;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.security.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController{
    private final ChatRoomService chatRoomService;
    private final JwtUtil jwtUtil;

    @GetMapping("/user")
    @ResponseBody
    public LoginInfo getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return LoginInfo.builder().nickname(name).token(jwtUtil.createToken(name, UserRoleEnum.USER)).build();
    }

    // 채팅 리스트 화면
//    @GetMapping("/room")
//    public String rooms(Model model) {
//        return "/chat/room";
//    }

    // 모든 채팅방 목록 반환 - 유저별
//    @GetMapping("/rooms")
//    public List<ChatRoomResponseDto> getAllChatRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return chatRoomService.getAllChatRooms(userDetails.getMember().getId());
//    }

    // 채팅방 생성 - 아이템 상세 페이지 -> 채팅하기 버튼 누르면 실행
    @PostMapping("/room/{itemId}")
    @ResponseBody
    public ChatRoomResponseDto createRoom(@PathVariable Long itemId,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatRoomService.createChatRoom(itemId, userDetails.getMember());
    }

    // 채팅방 단일 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom getRoom(@PathVariable String roomId){
        return chatRoomService.getRoom(roomId);
    }

    // 채팅방 상세 조회
//    @GetMapping("/room/{roomId}")
//    @ResponseBody
//    public ChatRoomResponseDto getChatRoomDetails(@PathVariable String roomId,
//                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return chatRoomService.getChatRoomDetails(roomId, userDetails.getMember());
//    }

    // 채팅방 입장 화면
    @GetMapping("/room/{roomId}")
    public ModelAndView roomDetail(Model model, @PathVariable String roomId,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Controller RoomId 1" + ":" + roomId);
//        chatRoomService.findRoomByRoomId(roomId);
//        chatRoomService.enterChatRoom(roomId, userDetails.getMember());
        log.info("Controller RoomId 2" + ":" + roomId);
        model.addAttribute("roomId", roomId);
        ModelAndView page = new ModelAndView("/chat/roomdetail");
        return page;
    }



}