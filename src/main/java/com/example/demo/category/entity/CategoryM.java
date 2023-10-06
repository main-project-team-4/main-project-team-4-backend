package com.example.demo.category.entity;

import com.example.demo.category.dto.CategoryRequestDto;
import com.example.demo.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "category_mid")
@Getter @Setter @NoArgsConstructor
public class CategoryM {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private CategoryL parent;

    @OneToMany(mappedBy = "categoryMidId")
    private List<Item> items = new ArrayList<>();

    public CategoryM(String name, CategoryL parent) {
        this.name = name;
        this.parent = parent;
    }

    public void update(CategoryRequestDto request) {
        String name = request.getName();
        if(name != null && !name.isBlank()) {
            this.name = name;
        }
    }
}
