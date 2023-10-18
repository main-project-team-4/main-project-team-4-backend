package com.example.demo.unit.repository;

import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.utils.EnableQuerydslTest;
import com.example.demo.utils.testcase.LoadTeatCaseFollow;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@DataJpaTest
@EnableQuerydslTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @LoadTeatCaseFollow
    @Test
    void findFollowersByMember_Id() {
        // given
        Long memberId = 4L;

        // when
        List<Member> followers = memberRepository.findFollowersByMember_Id(memberId);

        // then
        Set<Long> answers = new HashSet<>();
        answers.add(1L); answers.add(2L); answers.add(3L);

        assertEquals(3, followers.size());
        for (Member follower : followers) {
            log.info(String.valueOf(follower.getId()));
            assertTrue(answers.contains(follower.getId()));
        }

    }

    @LoadTeatCaseFollow
    @Test
    void findFollowingsByMember_Id() {
        // given
        Long memberId = 1L;

        // when
        List<Member> followings = memberRepository.findFollowingsByMember_Id(memberId);

        // then
        Set<Long> answers = new HashSet<>();
        answers.add(3L); answers.add(4L);

        assertEquals(2, followings.size());
        for (Member follower : followings) {
            log.info(String.valueOf(follower.getId()));
            assertTrue(answers.contains(follower.getId()));
        }

    }
}