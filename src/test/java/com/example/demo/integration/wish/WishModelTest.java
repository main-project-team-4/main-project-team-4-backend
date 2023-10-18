package com.example.demo.integration.wish;

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
public class WishModelTest {
    @Autowired
    private ItemService itemService;

    @Retention(RetentionPolicy.RUNTIME)
    @SqlGroup({
            @Sql({
                    "classpath:data/testcase-popular.sql"
            }),
            @Sql(
                    scripts = "classpath:truncate-testcases.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            )
    })
    @interface LoadTestCasePopular {}

    @LoadTestCasePopular
    @Test
    @DisplayName("[정상 작동] readPopularItems")
    void readPopularItems() {
        // given
        int num = 4;
        Pageable pageable = PageRequest.of(0, num);

        // when
        ResponseEntity<Page<ItemSearchResponseDto>> result = itemService.readPopularItems(pageable);

        // then
        assertThat(result.getBody())
                .hasSize(num)
                .extracting(ItemSearchResponseDto::getItemId)
                .isEqualTo(List.of(1L, 3L, 5L, 6L));
    }
}
