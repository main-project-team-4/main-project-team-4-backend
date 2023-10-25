package com.example.demo.unit.item;

import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.utils.EnableQuerydslTest;
import com.example.demo.utils.testcase.LoadTeatCaseCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@EnableQuerydslTest
class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @LoadTeatCaseCategory
    @Test
    @DisplayName("[정상 작동] findByCategoryLargeId 함수 정상 작동")
    void findByCategoryLargeId() {
        // given
        Long id = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Item> items = itemRepository.findByCategoryLargeId(id, pageable);

        // then
        items.stream().map(Item::getComment).forEach(log::info);
        assertThat(items.getTotalElements()).isEqualTo(4);
        for (Item item : items) {
            assertThat(item.getComment().contains("man")).isTrue();
        }
    }

    @LoadTeatCaseCategory
    @Test
    @DisplayName("[정상 작동] findByCategoryMiddleId 함수 정상 작동")
    void findByCategoryMiddleId() {
        // given
        Long id = 1L;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Item> items = itemRepository.findByCategoryMiddleId(id, pageable);

        // then
        items.stream().map(Item::getComment).forEach(log::info);
        assertThat(items.getTotalElements()).isEqualTo(2);
        for (Item item : items) {
            assertThat(item.getComment().contains("man shirt")).isTrue();
        }
    }

}