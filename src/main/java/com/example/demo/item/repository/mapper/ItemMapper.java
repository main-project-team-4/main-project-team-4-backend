package com.example.demo.item.repository.mapper;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Sort;

import static com.example.demo.item.entity.QItem.item;

public class ItemMapper implements Mapper {
    @Override
    public OrderSpecifier<?> map(Sort.Order order) {
        Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
        return switch (order.getProperty()) {
            case "id" -> new OrderSpecifier<>(direction, item.id);
            case "createdAt" -> new OrderSpecifier<>(direction, item.createdAt);
            case "name" -> new OrderSpecifier<>(direction, item.name);
            case "price" -> new OrderSpecifier<>(direction, item.price);
            default -> null;
        };
    }
}
