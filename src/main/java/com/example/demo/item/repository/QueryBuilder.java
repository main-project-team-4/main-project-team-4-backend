package com.example.demo.item.repository;

import com.example.demo.item.repository.mapper.Mapper;
import com.querydsl.core.types.OrderSpecifier;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class QueryBuilder {
    public OrderSpecifier<?>[] extractOrder(Mapper mapper, Pageable pageable) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        for (Sort.Order order : pageable.getSort()) {
            orders.add(mapper.map(order));
        }
        return orders.toArray(OrderSpecifier[]::new);
    }
}
