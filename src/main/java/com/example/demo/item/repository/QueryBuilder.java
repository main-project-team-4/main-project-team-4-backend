package com.example.demo.item.repository;

import com.example.demo.item.repository.mapper.Mapper;
import com.example.demo.location.entity.Location;
import com.example.demo.location.entity.QLocation;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.NumberExpression;
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

    public NumberExpression<Long> radius(Location center, QLocation data) {
        NumberExpression<Long> diffLatitude = data.latitude.subtract(center.getLatitude());
        NumberExpression<Long> diffLongitude = data.longitude.subtract(center.getLongitude());

        NumberExpression<Long> latitudeSquare = diffLatitude.multiply(diffLatitude);
        NumberExpression<Long> longitudeSquare = diffLongitude.multiply(diffLongitude);
        return latitudeSquare.add(longitudeSquare);
    }
}
