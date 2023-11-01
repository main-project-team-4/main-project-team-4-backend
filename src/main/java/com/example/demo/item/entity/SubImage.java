package com.example.demo.item.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;

@Entity @Table(name = "item_sub_images")
@Getter @Setter @NoArgsConstructor
public class SubImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "sub_image_url")
    private URL url;

    @Column(name = "sub_images_order")
    private Integer order;

    public SubImage(Item item, URL subImageUrl) {
        this.item = item;
        this.url = subImageUrl;
        this.order = item.getSubImageList().size();
    }
}
