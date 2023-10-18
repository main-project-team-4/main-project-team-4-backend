package com.example.demo.unit.item;

import com.amazonaws.util.IOUtils;
import com.example.demo.dto.MessageResponseDto;
import com.example.demo.item.controller.ItemController;
import com.example.demo.item.dto.itemRequestDto;
import com.example.demo.item.service.ItemService;
import com.example.demo.utils.WithMockPrincipal;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ItemControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemService itemService;
    @InjectMocks
    private ItemController itemController;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockPrincipal
    @Test
    @DisplayName("[정상 작동] POST /api/items")
    void createItem() throws Exception {
        // given
        MockMultipartFile mainImage = getMockMultipartFile("swirls.jpg", "main_image");
        List<MockMultipartFile> subImage = List.of(getMockMultipartFile("swirls.jpg", "sub_image"));
        itemRequestDto dto = new itemRequestDto();

        MockMultipartHttpServletRequestBuilder builder = multipart("/api/items")
                .file(mainImage);
        for (MockMultipartFile file : subImage) {
            builder = builder.file(file);
        }
        MockHttpServletRequestBuilder requestBuilder = builder
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .param("requestDto", objectMapper.writeValueAsString(dto))
                .with(csrf());

        ResponseEntity<MessageResponseDto> response = ResponseEntity.ok(new MessageResponseDto("mock response", 200));

        when(itemService.createItem(any(), any(), any(), any()))
                .thenReturn(response);

        // when & then
        mvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    private MockMultipartFile getMockMultipartFile(String path, String key) throws IOException {
        File imageFile = new File(path);

        return new MockMultipartFile(key, IOUtils.toByteArray(new FileInputStream(imageFile)));
    }
}
