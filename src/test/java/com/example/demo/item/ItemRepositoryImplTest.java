package com.example.demo.item;

import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.ItemRepositoryImpl;
import com.example.demo.utils.EnableQuerydslTest;
import com.example.demo.utils.LoadTeatCaseCategory;
import com.example.demo.utils.LoadTeatCasePopular;
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
class ItemRepositoryImplTest {
    @Autowired
    private ItemRepositoryImpl itemRepository;

    @LoadTeatCaseCategory
    @Test
    @DisplayName("[정상 작동] searchBy 함수 - 전체 조회")
    void searchBy_loadAllItem() {
        // given
        String keyword = null;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Item> items = itemRepository.searchBy(keyword, pageable);

        // then
        int numExpected = 8;

        items.stream().map(Item::getComment).forEach(log::info);
        assertThat(items.getTotalElements()).isEqualTo(numExpected);
    }

    @LoadTeatCaseCategory
    @Test
    @DisplayName("[정상 작동] searchBy 함수 - 키워드로 조회")
    void searchBy_loadWithKeyword() {
        // given
        String keyword = "ean";
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<Item> items = itemRepository.searchBy(keyword, pageable);

        // then
        int numExpected = 4;

        items.stream().map(Item::getComment).forEach(log::info);
        assertThat(items.getTotalElements()).isEqualTo(numExpected);
        for (Item item : items) {
            assertThat(item.getName().contains(keyword)).isTrue();
        }
    }

    @LoadTeatCaseCategory
    @Test
    @DisplayName("[정상 작동] searchBy 함수 - 페이지 조회")
    void searchBy_pagination() {
        // given
        String keyword = null;
        Pageable pageable = PageRequest.of(3, 2);

        // when
        Page<Item> items = itemRepository.searchBy(keyword, pageable);

        // then
        Item item = items.getContent().get(0);
        assertThat(item.getName()).isEqualTo("jean3");
    }

    @LoadTeatCasePopular
    @Test
    @DisplayName("[정상 작동] findPopularItems 함수")
    void findPopularItems() {
        // given
        Pageable pageable = PageRequest.of(0, 4);

        // when
        Page<Item> items = itemRepository.findPopularItems(pageable);

        // then
        List<Long> expected = List.of(1L, 3L, 5L, 6L);
        List<Long> answers = items.map(Item::getId).getContent();
        assertThat(answers).isEqualTo(expected);
    }
}