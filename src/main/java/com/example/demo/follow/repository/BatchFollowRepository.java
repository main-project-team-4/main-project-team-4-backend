package com.example.demo.follow.repository;

import com.example.demo.follow.entity.Follow;

import java.util.Collection;
import java.util.List;

public interface BatchFollowRepository {
    List<Follow> findAllByShop_Id(Collection<Long> shopIdList);
}
