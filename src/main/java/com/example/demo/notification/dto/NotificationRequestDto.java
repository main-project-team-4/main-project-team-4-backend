package com.example.demo.notification.dto;

import com.example.demo.member.entity.Member;
import com.example.demo.notification.Entity.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class NotificationRequestDto {
    private Member receiver;
    private Member sender;
    NotificationType notificationType;
    private String content;
    private Long itemId;

    public NotificationRequestDto(Member receiver, Member sender, NotificationType notificationType,
                                  String content, Long itemId) {
        this.receiver = receiver;
        this.sender = sender;
        this.notificationType = notificationType;
        this.content = content;
        this.itemId = itemId;
    }
}