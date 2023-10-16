package com.example.demo.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/chat/room")       // 접속 주소
    public String getChatRooms() {
        return "chat/room";            // html
    }

//    @GetMapping("chat/room")
//    public String enterChatRoom() {
//        return "chat/roomdetail";
//    }
}