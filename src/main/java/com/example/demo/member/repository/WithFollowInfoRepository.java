package com.example.demo.member.repository;

import com.example.demo.member.dto.MemberWithFollowMapper;

import java.util.Optional;

public interface WithFollowInfoRepository {
    Optional<MemberWithFollowMapper> findWithFollowInfoById(Long id);
}
