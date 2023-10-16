package com.example.demo.chat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController{
    @GetMapping("/chat/room") // 상점 수정 및 삭제
    public String getChatRooms() {
        return "chat/room";
    }
}