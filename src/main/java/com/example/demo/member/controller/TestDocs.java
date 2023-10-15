package com.example.demo.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(
        name = "Test API",
        description = "오직 Test때만 사용하는 API"
)
public interface TestDocs {
    @Operation(
            summary = "JWT 발급 API",
            description = """
                    테스트를 위한 JWT 발급 API.<br>
                    username에 발급을 원하는 회원의 ID 값을 넣으면,<br>
                    해당 User의 JWT가 발급되어 테스트에서 사용 가능합니다.<br>
                    당연히, 해당 유저는 DB에 저장되어 있어야 합니다.<br>
                    만약 회원 가입없이 JWT를 발급받고 싶다면 백엔드 팀에 요청하시면 됩니다.<br>
                    발급해드린 JWT의 만료기한을 3000년 1월 1일로 해둔거니까<br>
                    귀찮으시면 메모장에 JWT 저장해놓고 사용하셔도 무방합니다.<br>
                    """
    )
    ResponseEntity<String> getAccessTokenForTest(String username);
}
