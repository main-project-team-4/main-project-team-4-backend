package com.example.demo.wish.repository;

import com.example.demo.member.entity.Member;
import com.example.demo.utils.EnableQuerydslTest;
import com.example.demo.utils.LoadTeatCaseWish;
import com.example.demo.wish.entity.Wish;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@EnableQuerydslTest
class WishRepositoryTest {
    @Autowired
    private WishRepository wishRepository;

    @LoadTeatCaseWish
    @Test
    @DisplayName("[정상 작동] MemberId와 ItemId가 주어지면 해당 찜 데이터 가져오기")
    void findByMember_IdAndItem_Id() {
        // given
        Long memberId = 1L;
        Long itemId = 1L;

        // when
        Optional<Wish> wishList = wishRepository.findByMember_IdAndItem_Id(memberId, itemId);

        // then
        int totalNum = 11;

        assertTrue(wishList.isPresent());
        Long id = wishList.get().getId();
        log.info(String.valueOf(id));
    }

    @LoadTeatCaseWish
    @Test
    @DisplayName("[정상 작동] Member 이름이 주어지면 해당 멤버의 찜 목록 가져오기")
    void findByMember() {
        // given
        Member member = new Member();
        member.setId(2L);

        // when
        List<Wish> wishList = wishRepository.findByMember(member);

        // then
        HashSet<Long> answers = new HashSet<>();
        answers.add(1L); answers.add(3L);answers.add(5L);answers.add(7L);

        assertEquals(wishList.size(), 4);
        for (Wish wish : wishList) {
            Long id = wish.getItem().getId();

            log.info(String.valueOf(id));
            assertTrue(answers.contains(id));
        }
    }

    @LoadTeatCaseWish
    @Test
    @DisplayName("[정상 작동] 찜 존재 확인")
    void existsByMember_IdAndItem_IdReturnTrue() {
        // given
        Long itemId = 1L;
        Long memberId = 1L;

        // when
        boolean isWished = wishRepository.existsByMember_IdAndItem_Id(memberId, itemId);

        // then
        assertTrue(isWished);
    }

    @LoadTeatCaseWish
    @Test
    @DisplayName("[정상 작동] 찜 존재하지 않음을 확인")
    void existsByMember_IdAndItem_IdReturnFalse() {
        // given
        Long itemId = 8L;
        Long memberId = 1L;

        // when
        boolean isWished = wishRepository.existsByMember_IdAndItem_Id(memberId, itemId);

        // then
        assertFalse(isWished);
    }
}