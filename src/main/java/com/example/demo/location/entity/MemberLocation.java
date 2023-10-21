package com.example.demo.location.entity;

import com.example.demo.location.dto.LocationRequestDto;
import com.example.demo.location.listener.LocationListener;
import com.example.demo.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@EntityListeners({LocationListener.class})
public class MemberLocation extends Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberLocation(LocationRequestDto requestDto, Member member) {
        this.name = requestDto.getName();
        this.member = member;
    }

    public MemberLocation(Member member) {
        this.member = member;
    }
}
