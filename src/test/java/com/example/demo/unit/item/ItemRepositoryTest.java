package com.example.demo.unit.item;

import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.utils.EnableQuerydslTest;
import com.example.demo.utils.LoadTeatCaseCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

        // when
        List<Item> items = itemRepository.findByCategoryLargeId(id);

        // then
        items.stream().map(Item::getComment).forEach(log::info);
        assertThat(items.size()).isEqualTo(4);
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

        // when
        List<Item> items = itemRepository.findByCategoryMiddleId(id);

        // then
        items.stream().map(Item::getComment).forEach(log::info);
        assertThat(items.size()).isEqualTo(2);
        for (Item item : items) {
            assertThat(item.getComment().contains("man shirt")).isTrue();
        }
    }

}