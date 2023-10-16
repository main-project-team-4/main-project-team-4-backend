package com.example.demo.integration.member;


import com.example.demo.member.dto.MemberInfoRequestDto;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.utils.LoadEnvironmentVariables;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@LoadEnvironmentVariables
public class MemberApiTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberRepository memberRepository;

    @Sql(
            scripts = {"classpath:testcase-member.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Test
    @DisplayName("[정상 작동] GET /api/auth/members/me")
    void read_run() throws Exception {
        // given
        String url = "/api/auth/members/me";

        // -> username이 1인 유저의 JWT
        Long memberId = 1L;
        String jwt = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiYXV0aCI6IlVTRVIiLCJleHAiOjMyNTA2MzU4NDAwLCJpYXQiOjE2OTc0MzgyNzh9.ywtvcTO_CndQgIgwXok0Ow6PofiAuq3u_bMbtpltS8I";

        MemberInfoRequestDto requestDto = new MemberInfoRequestDto();
        requestDto.setNickname("바꿀 아이디");
        String requestBody = objectMapper.writeValueAsString(requestDto);

        // when & then
        mvc.perform(
                        put(url)
                                .header(HttpHeaders.AUTHORIZATION, jwt)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )

                // Test 하기 전 로그 찍기.
                .andDo(print())

                // 실제로 내가 원하는 값이 나오는 지 확인하기.
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value("200"))
        ;

        // 실제로 DB에서 변경 되었는 지 확인
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("조회할 수 없음."));
        assertThat(member.getNickname()).isEqualTo("바꿀 아이디");
    }
}
