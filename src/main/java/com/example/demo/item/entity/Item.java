package com.example.demo.item.entity;

import com.example.demo.category.entity.CategoryM;
import com.example.demo.chat.entity.ChatRoom;
import com.example.demo.entity.TimeStamp;
import com.example.demo.location.entity.ItemLocation;
import com.example.demo.review.entity.Review;
import com.example.demo.shop.entity.Shop;
import com.example.demo.trade.entity.Trade;
import com.example.demo.trade.type.State;
import com.example.demo.wish.entity.Wish;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.net.MalformedURLException;
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

    @OneToMany(mappedBy = "item", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<SubImage> subImageList = new ArrayList<>();

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

    @OneToMany(mappedBy = "item", cascade = {CascadeType.REMOVE})
    @Column(name = "wish_list")
    private List<Wish> wishList = new ArrayList<>();

    @OneToMany(mappedBy = "item", orphanRemoval = true)
    private List<ChatRoom> itemChatRoom = new ArrayList<>();

    @OneToOne(mappedBy = "item", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private ItemLocation itemLocation;

    @OneToMany(mappedBy = "item", cascade = {CascadeType.REMOVE})
    private List<Trade> tradeList = new ArrayList<>();

    @OneToMany(mappedBy = "item", orphanRemoval = true)
    private List<Review> reviewList = new ArrayList<>();

    public Item(String name, int price, String comment, URL main_image, List<URL> sub_images, Shop shop, Boolean withDeliveryFee, CategoryM categoryM) {
        this.id = getId();
        this.shop = shop;
        this.name = name;
        this.price = price;
        this.comment = comment;
        this.main_image = main_image;
        this.addAllSubImagesUrl(sub_images);
        this.withDeliveryFee = withDeliveryFee;
        this.categoryMidId = categoryM;
    }

    public List<URL> getSub_images() {
        return this.getSubImageList().stream()
                .map(SubImage::getUrl)
                .toList();
    }

    public void addSubImage(URL subImageUrl) {
        SubImage subImage = new SubImage(this, subImageUrl);
        this.getSubImageList().add(subImage);
    }

    public void addAllSubImagesString(List<String> subImageUrlList) throws MalformedURLException {
        for (String subImageUrl : subImageUrlList) {
            this.addSubImage(new URL(subImageUrl));
        }
    }

    public void addAllSubImagesUrl(List<URL> subImageUrlList) {
        for (URL subImageUrl : subImageUrlList) {
            this.addSubImage(subImageUrl);
        }
    }

    public void update(String name, int price, String comment, Boolean withDeliveryFee, CategoryM categoryM) {
        this.name = name;
        this.price = price;
        this.comment = comment;
//        this.main_image = main_image;
//        this.sub_images.addAll(sub_images);
        this.withDeliveryFee = withDeliveryFee;
        this.categoryMidId = categoryM;
    }

    public void updateMainImage(URL main_image) {
        this.main_image = main_image;
    }

    public List<URL> getImageList() {
        URL mainImage = this.getMain_image();
        List<URL> subImageList = this.getSub_images();

        List<URL> urlList = new ArrayList<>();
        urlList.add(mainImage);
        urlList.addAll(subImageList);

        return urlList;
    }

}
