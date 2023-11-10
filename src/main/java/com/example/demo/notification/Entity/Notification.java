package com.example.demo.notification.Entity;

import com.example.demo.entity.TimeStamp;
import com.example.demo.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.net.URL;

@Getter
@Entity
@NoArgsConstructor
public class Notification extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private NotificationContent content;

    private String url;

    @Column(nullable = false)
    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member receiver;

    private URL imageUrl;

    @Builder
    public Notification(Member receiver, NotificationType notificationType, String content, String url, URL imageUrl) {
        this.receiver = receiver;
        this.notificationType = notificationType;
        this.content = new NotificationContent(content);
        this.url = url;
        this.isRead = false;
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content.getContent();
    }

//    public String getUrl() {
//        return url.getUrl();
//    }

    public void read(){
        isRead = true;
    }
}