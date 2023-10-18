package com.example.demo.unit.category.repository;

import com.example.demo.category.entity.CategoryL;
import com.example.demo.category.repository.CategoryLRepository;
import com.example.demo.utils.EnableQuerydslTest;
import com.example.demo.utils.testcase.LoadTeatCaseCategory;
import jakarta.persistence.EntityManager;
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
class CategoryLRepositoryTest {
    @Autowired
    private CategoryLRepository categoryLRepository;
    @Autowired
    private EntityManager em;

    @LoadTeatCaseCategory
    @Test
    @DisplayName("[정상 작동] 재귀적 카테고리 호출.")
    void findAllRecursively() {
        // given

        // when
        List<CategoryL> categoryList = categoryLRepository.findAllRecursively();

        // then
        for (CategoryL categoryL : categoryList) {
            em.detach(categoryL);

            assertThat(categoryL.getCategoryMiddles()).isNotNull();
        }

    }
}