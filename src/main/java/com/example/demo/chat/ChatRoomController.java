package com.example.demo.chat;

import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.member.entity.Member;
import com.example.demo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController{
    private final ChatRoomService chatRoomService;

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
    public ChatRoom createRoom(@PathVariable Long itemId,
                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return chatRoomService.createChatRoom(itemId, userDetails.getMember());
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
        model.addAttribute("roomId", roomId);
        chatRoomService.findRoomByRoomId(roomId);
        chatRoomService.enterChatRoom(roomId, userDetails.getMember());
        ModelAndView page = new ModelAndView("/chat/roomdetail");
        return page;
    }


    // 밑은 인터넷에서 가져온거

    // 전체 룸 조회
    @GetMapping("/rooms")
    public List<ChatRoom> rooms() {
        return chatRoomService.findAllRoom();
    }

//    @GetMapping("/room/{roomId}")
//    public ChatRoom roomInfo(@PathVariable String roomId) {
//        return chatRoomService.findRoomByRoomId(roomId);
//    }

    // 유저별 채팅방 조회
    @GetMapping("/customer")
    public List<ChatRoom> getRoomsByCustomer(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Member consumer = userDetails.getMember();
        return chatRoomService.getConsumerEnterRooms(consumer);
    }

    // store 별 채팅방 조회
    @GetMapping("/store")
    public List<ChatRoom> getRoomsBySeller(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member seller = userDetails.getMember();
        return chatRoomService.getSellerEnterRooms(seller);
    }
}