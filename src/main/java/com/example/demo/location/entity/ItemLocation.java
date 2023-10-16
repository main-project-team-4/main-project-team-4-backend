package com.example.demo.location.entity;

import com.example.demo.item.entity.Item;
import com.example.demo.location.listener.LocationListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@EntityListeners({LocationListener.class})
public class ItemLocation extends Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
