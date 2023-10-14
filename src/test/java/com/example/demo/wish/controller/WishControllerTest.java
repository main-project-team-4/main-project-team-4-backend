package com.example.demo.wish.controller;

import com.example.demo.item.service.ItemService;
import com.example.demo.utils.WithMockPrincipal;
import com.example.demo.wish.service.WishService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.demo.utils.ProxyTestRequest.postAsJson;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WishController.class)
@MockBean(JpaMetamodelMappingContext.class)
class WishControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private WishService wishService;
    @MockBean
    private ItemService itemService;

    @InjectMocks
    private WishController wishController;

    @WithMockPrincipal
    @Test
    @DisplayName("[정상 작동] /api/items/{itemId}/wishes")
    void toggleWish() throws Exception {
        // given

        // when
        mvc.perform(postAsJson("/api/items/1/wishes", null))
                .andDo(print())
                .andExpect(status().isOk());

    }
}