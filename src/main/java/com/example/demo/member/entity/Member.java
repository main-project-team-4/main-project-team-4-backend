package com.example.demo.member.entity;

import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.follow.entity.Follow;
import com.example.demo.location.entity.Location;
import com.example.demo.shop.entity.Shop;
import jakarta.persistence.*;
import lombok.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Entity @Table
@Getter @Setter @NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone_num")
    private String phoneNum;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Location> locations = new ArrayList<>();

    @Column(name = "image", nullable = true)
    private URL image;

    @OneToOne(mappedBy = "member")
    private Shop shop;

    @OneToMany(mappedBy = "member")
    private List<Follow> followList = new ArrayList<>();

    @OneToMany(mappedBy = "seller")
    private List<ChatRoom> sellerChatRoomList = new ArrayList<>();

    @OneToMany(mappedBy = "consumer")
    private List<ChatRoom> consumerChatRoomList = new ArrayList<>();

    public Member(String username, String password, String nickname, String phoneNum, List<Location> locations) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.phoneNum = phoneNum;

        for (Location location : locations) {
            location.setMember(this);
        }
        this.locations.addAll(locations);
    }



    public void updateMember(String username, String password, String nickname, String phoneNum, URL image) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.phoneNum = phoneNum;
        this.image = image;
    }

    public void update(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }



}
