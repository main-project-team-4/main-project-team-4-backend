package com.example.demo.item.entity;

import com.example.demo.category.entity.CategoryM;
import com.example.demo.chat.entity.ChatMessage;
import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.entity.TimeStamp;
import com.example.demo.member.entity.Member;
import com.example.demo.shop.entity.Shop;
import com.example.demo.location.entity.Location;
import com.example.demo.trade.type.State;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "item")
public class Item extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String comment;

    @Column(name = "mainImage_url")
    private URL main_image;

    @ElementCollection
    @CollectionTable(name = "item_sub_images", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "subImage_url")
    private List<URL> sub_images = new ArrayList<>(); // 리스트 필드 초기화

    @Column(name = "state")
    private State state;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_mid_id")
    private CategoryM categoryMidId;

    @OneToMany(mappedBy = "item")
    private List<ChatRoom> itemChatRoom = new ArrayList<>();

    public Item(String name, int price, String comment, URL main_image, List<URL> sub_images, Shop shop) {
        this.id = getId();
        this.shop = shop;
        this.name = name;
        this.price = price;
        this.comment = comment;
        this.main_image = main_image;
        this.sub_images.addAll(sub_images);
    }

    public void updateSubImage(List<URL> sub_images) {
        this.sub_images.addAll(sub_images);
    }

    public void update(String name, int price, String comment, URL main_image, List<URL> sub_images) {
        this.name = name;
        this.price = price;
        this.comment = comment;
        this.main_image = main_image;
        this.sub_images.addAll(sub_images);
    }
}
