package com.example.demo.item.entity;

import com.example.demo.category.entity.CategoryM;
import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.entity.TimeStamp;
import com.example.demo.location.entity.ItemLocation;
import com.example.demo.shop.entity.Shop;
import com.example.demo.trade.type.State;
import com.example.demo.wish.entity.Wish;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Item extends TimeStamp implements Serializable {
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
    private State state = State.SELLING;

    @Column(name = "with_delivery_fee")
    private Boolean withDeliveryFee;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_mid_id")
    private CategoryM categoryMidId;

    @OneToMany(mappedBy = "item")
    @Column(name = "wish_list")
    private List<Wish> wishList = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<ChatRoom> itemChatRoom = new ArrayList<>();

    @OneToOne(mappedBy = "item", cascade = CascadeType.PERSIST)
    private ItemLocation itemLocation;

    public Item(String name, int price, String comment, URL main_image, List<URL> sub_images, Shop shop, Boolean withDeliveryFee, CategoryM categoryM) {
        this.id = getId();
        this.shop = shop;
        this.name = name;
        this.price = price;
        this.comment = comment;
        this.main_image = main_image;
        this.sub_images.addAll(sub_images);
        this.withDeliveryFee = withDeliveryFee;
        this.categoryMidId = categoryM;
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
