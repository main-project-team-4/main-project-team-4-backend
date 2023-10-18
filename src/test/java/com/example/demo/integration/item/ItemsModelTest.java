package com.example.demo.integration.item;

import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.item.service.ItemService;
import com.example.demo.utils.LoadEnvironmentVariables;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@LoadEnvironmentVariables
public class ItemsModelTest {
    @Autowired
    private ItemService itemService;

    @Retention(RetentionPolicy.RUNTIME)
    @SqlGroup({
            @Sql({
                    "classpath:data/testcase-search.sql"
            }),
            @Sql(
                    scripts = "classpath:truncate-testcases.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            )
    })
    @interface LoadTestCaseSearch {}

    @LoadTestCaseSearch
    @Test
    @DisplayName("[정상 작동] searchItem - keyword")
    void searchItem_whenGivenKeyword() {
        // given
        String keyword = "cket";
        Pageable pageable = PageRequest.of(0, 10);

        // when
        ResponseEntity<Page<ItemSearchResponseDto>> result = itemService.searchItem(keyword, pageable);

        // then
        assertThat(result.getBody())
                .hasSize(4)
                .extracting(ItemSearchResponseDto::getItemId)
                .allSatisfy(
                        id -> assertThat(id).isIn(1L, 2L, 3L, 4L)
                );
    }

    @LoadTestCaseSearch
    @Test
    @DisplayName("[정상 작동] searchItem - sort by createdAt")
    void searchItem_whenSortedByCreated() {
        // given
        String keyword = null;
        Pageable pageable = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "createdAt"));

        // when
        ResponseEntity<Page<ItemSearchResponseDto>> result = itemService.searchItem(keyword, pageable);

        // then
        assertThat(result.getBody().getContent())
                .hasSize(8)
                .extracting(ItemSearchResponseDto::getItemId)
                .isEqualTo(List.of(6L, 5L, 4L, 1L, 2L, 3L, 8L, 7L));
    }
}
