package com.example.demo.integration.trade;

import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.trade.service.TradeService;
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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@LoadEnvironmentVariables
public class TradeModelTest {
    @Autowired
    private TradeService tradeService;

    @Autowired
    private MemberRepository memberRepository;

    @Retention(RetentionPolicy.RUNTIME)
    @SqlGroup({
            @Sql({
                    "classpath:data/testcase-trade.sql"
            }),
            @Sql(
                    scripts = "classpath:truncate-testcases.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            )
    })
    @interface LoadTestCaseTrade {}

    @LoadTestCaseTrade
    @Test
    @DisplayName("[정상 작동] readOrders")
    void readOrders() {
        // given
        Member member = memberRepository.findById(1L).orElseThrow();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        ResponseEntity<Page<ItemSearchResponseDto>> result = tradeService.readOrders(member, pageable);

        // then
        assertThat(result.getBody().getContent())
                .extracting(ItemSearchResponseDto::getItemId)
                .containsAnyElementsOf(List.of(2L, 3L, 4L));
    }

    @LoadTestCaseTrade
    @Test
    @DisplayName("[정상 작동] readSales")
    void readSales() {
        // given
        Member member = memberRepository.findById(1L).orElseThrow();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        ResponseEntity<Page<ItemSearchResponseDto>> result = tradeService.readSales(member, pageable);

        // then
        assertThat(result.getBody().getContent())
                .extracting(ItemSearchResponseDto::getItemId)
                .containsAnyElementsOf(List.of(1L, 2L));
    }
}
