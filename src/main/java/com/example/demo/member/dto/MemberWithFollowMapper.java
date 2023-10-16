package com.example.demo.member.dto;

import com.example.demo.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberWithFollowMapper {
    private Member member;
    private Long numOfFollowers;
    private Long numOfFollowings;

    public MemberWithFollowMapper(
            Member member,
            Long numOfFollowers,
            Long numOfFollowings
    ) {
        this.member = member;
        this.numOfFollowers = numOfFollowers;
        this.numOfFollowings = numOfFollowings;
    }
}
