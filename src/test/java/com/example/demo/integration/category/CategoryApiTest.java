package com.example.demo.integration.category;


import com.example.demo.utils.LoadEnvironmentVariables;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@LoadEnvironmentVariables
public class CategoryApiTest {
    @Autowired
    private MockMvc mvc;

    @Test @Sql(scripts = {"classpath:testcase-category.sql"})
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
                .andExpect(jsonPath("$.category_id").value("1"))
                .andExpect(jsonPath("$.category_name").value("man"))
        ;
    }

    @Test @Sql(scripts = {"classpath:testcase-category.sql"})
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
                .andExpect(status().isBadRequest())
        ;
    }

    @Test @Sql(scripts = {"classpath:testcase-category.sql"})
    @DisplayName("[비정상 작동] GET /api/categories/{categoryId} - 존재하지 않는 layer 요구")
    void read_occur_error_when_wrong_layer() throws Exception {
        String notExistedLayer = "10000";

        mvc.perform(
                        get("/api/categories/1")
                                .param("layer", notExistedLayer)
                ).andDo(print())

                .andExpect(status().isBadRequest());
    }

    @Test @Sql(scripts = {"classpath:testcase-category.sql"})
    @DisplayName("[정상 작동] GET /api/categories/{categoryId}/categories")
    void readChildCategory_run() throws Exception {
        int numOfMiddleCategoryOf1stChildren = 2;

        mvc.perform(
                        get("/api/categories/1/categories")
                ).andDo(print())

                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(numOfMiddleCategoryOf1stChildren)))
                .andExpect(jsonPath("$[*].category_id").hasJsonPath())
                .andExpect(jsonPath("$[*].category_name").hasJsonPath());
    }

    @Test @Sql(scripts = {"classpath:testcase-category.sql"})
    @DisplayName("[정상 작동] GET /api/categories/{categoryId}/items - 기본 설정값은 layer 2")
    void readChildItem_run_when_no_layer() throws Exception {
        int numOfMiddleCategoryOf1stItem = 2;
        List<String> middleCategoryOf1stItemIdList = List.of("jacket1", "jacket2");

        mvc.perform(
                        get("/api/categories/1/items")
                ).andDo(print())

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]").value(hasSize(numOfMiddleCategoryOf1stItem)))
                .andExpect(jsonPath("$[*].name").value(everyItem(in(middleCategoryOf1stItemIdList))));
    }

    @Test @Sql(scripts = {"classpath:testcase-category.sql"})
    @DisplayName("[정상 작동] GET /api/categories/{categoryId}/items - 중분류 item 조회")
    void readChildItem_run_when_layer_2() throws Exception {
        int numOfMiddleCategoryOf1stItem = 2;
        List<String> middleCategoryOf1stItemIdList = List.of("jacket1", "jacket2");

        mvc.perform(
                        get("/api/categories/1/items")
                                .param("layer", "2")
                ).andDo(print())

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]").value(hasSize(numOfMiddleCategoryOf1stItem)))
                .andExpect(jsonPath("$[*].name").value(everyItem(in(middleCategoryOf1stItemIdList))));
    }

    @Test @Sql(scripts = {"classpath:testcase-category.sql"})
    @DisplayName("[정상 작동] GET /api/categories/{categoryId}/items - 대분류 item 조회")
    void readChildItem_run_when_layer_1() throws Exception {
        int numOfLargeCategoryOf1stItem = 4;
        List<String> largeCategoryOf1stItemIdList = List.of("jacket1", "jacket2", "jean1", "jean2");

        mvc.perform(
                        get("/api/categories/1/items")
                                .param("layer", "1")
                ).andDo(print())

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]").value(hasSize(numOfLargeCategoryOf1stItem)))
                .andExpect(jsonPath("$[*].name").value(everyItem(in(largeCategoryOf1stItemIdList))));
    }

    @Test @Sql(scripts = {"classpath:testcase-category.sql"})
    @DisplayName("[정상 작동] GET /api/categories/all/categories")
    void readCategoryRecursively_run() throws Exception {
        mvc.perform(
                        get("/api/categories/all/categories")
                                .param("layer", "1")
                ).andDo(print())

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].category_l_id").exists())
                .andExpect(jsonPath("$[*].category_l_name").exists())
                .andExpect(jsonPath("$[*].children.[*].category_m_id").exists())
                .andExpect(jsonPath("$[*].children.[*].category_m_name").exists())
                .andExpect(jsonPath("$[*].children.[*].category_l_id").exists())
                .andExpect(jsonPath("$[*].children.[*].category_l_name").exists())

                .andExpect(jsonPath("$[?(@.category_l_id == '1')].children.[*].category_l_id").value(everyItem(is(1))));
    }
}
