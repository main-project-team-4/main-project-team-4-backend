package com.example.demo.chat.entity;

import com.example.demo.item.entity.Item;
import com.example.demo.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chatroom")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom implements Serializable {
    private static final long serialVersionUID = 6494678977089006639L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    @Column(name = "room_name")     // TODO : 추후 삭제 가능
    private String roomName;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumer_id")
    private Member consumer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // 1 - seller is out, 2 - consumer is out
    @Column(name = "is_out")
    private Integer isOut;

    public ChatRoom (Item item, Member member){
        this.consumer = member;
        this.item = item;
        this.seller = item.getShop().getMember();
        this.roomName = consumer.getNickname() + " 님의 " + item.getName() + " 문의";
        this.isOut = 0;
    }

    public void isOutUpdate(int isOut){
        this.isOut = isOut;
    }
}