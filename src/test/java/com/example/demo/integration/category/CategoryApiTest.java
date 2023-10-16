package com.example.demo.integration.category;


import com.example.demo.utils.LoadEnvironmentVariables;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@LoadEnvironmentVariables
public class CategoryApiTest {
    @Autowired
    private MockMvc mvc;

    @Sql(
            scripts = {"classpath:testcase-category.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Test
    @DisplayName("[정상 작동] GET /api/categories/{categoryId}")
    void read_run() throws Exception {
        // given
        String url = "/api/categories/1";

        // when & then
        mvc.perform(
                        get(url)
                                .param("layer", "1")
                )

                // Test 하기 전 로그 찍기.
                .andDo(print())

                // 실제로 내가 원하는 값이 나오는 지 확인하기.
                .andExpect(status().isOk())
                // JsonPath 관련 문법 레퍼런스
                // https://seongjin.me/how-to-use-jsonpath-in-kubernetes/
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("man"))
        ;
    }

    @Sql(
            scripts = {"classpath:testcase-category.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
//    @Test
    @DisplayName("[비정상 작동] GET /api/categories/{categoryId} - 존재하지 않는 ID 요구")
    void read_occur_error_when_wrong_category_id() throws Exception {
        // given
        String url = "/api/categories/100000";

        // when & then
        mvc.perform(
                        get(url)
                                .param("layer", "1")
                )

                // Test 하기 전 로그 찍기.
                .andDo(print())

                // 실제로 내가 원하는 값이 나오는 지 확인하기.
                .andExpect(status().is4xxClientError())
        ;
    }
}
