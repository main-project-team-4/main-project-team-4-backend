package com.example.demo.item.repository.mapper;

import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Sort;

@FunctionalInterface
public interface Mapper {
    OrderSpecifier<?> map(Sort.Order order);
}
