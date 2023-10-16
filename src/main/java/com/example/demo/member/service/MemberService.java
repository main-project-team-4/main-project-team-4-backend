package com.example.demo.member.service;

import com.example.demo.dto.MessageResponseDto;
import com.example.demo.item.service.S3Uploader;
import com.example.demo.location.entity.MemberLocation;
import com.example.demo.member.dto.LocationRequestDto;
import com.example.demo.member.dto.MemberInfoRequestDto;
import com.example.demo.member.dto.MyPageMemberResponseDto;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final S3Uploader s3Uploader;

     public ResponseEntity<MessageResponseDto> deleteMember(Member member) {
        memberRepository.delete(member);

        MessageResponseDto msg = new MessageResponseDto("회원탈퇴에 성공하였습니다.", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(msg);
     }

    @Transactional
    public ResponseEntity<MessageResponseDto> updateMember(
            MemberInfoRequestDto request,
            Member memberLoggedIn
    ) {
        // 닉네임 변경.
        changeNickname(memberLoggedIn, request.getNickname());

        // 변경된 내용 저장.
        memberRepository.save(memberLoggedIn);

        MessageResponseDto msg = new MessageResponseDto("회원정보 수정에 성공하였습니다.", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    private void changeNickname(Member member, String nickname) {
        if(!StringUtils.hasText(nickname)) return;

        validateUniqueNickname(nickname);
        member.setNickname(nickname);
    }

    private void validateUniqueNickname(String nickname) {
        Optional<Member> checkUsername = memberRepository.findByNickname(nickname);
        if(checkUsername.isPresent()){
            throw new IllegalArgumentException("중복된 Nickname 입니다.");
        }
    }

    @Transactional
    public ResponseEntity<MessageResponseDto> updateMemberLocation(LocationRequestDto request, Member member) {
        Member saved = memberRepository.save(member);

        MemberLocation memberLocation = saved.getLocation();
        memberLocation.setName(request.getLocation());

        MessageResponseDto msg = new MessageResponseDto("회원 위치 정보 수정에 성공하였습니다.", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    @Transactional
    public ResponseEntity<MessageResponseDto> updateProfileImage(MultipartFile image, Member member) {
        URL url = s3Uploader.getUrlFromMultipartFile(image);
        member.setImage(url);

        memberRepository.save(member);
        MessageResponseDto msg = new MessageResponseDto("회원 프로필 사진 수정에 성공하였습니다.", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    @Transactional
    public ResponseEntity<MyPageMemberResponseDto> readMyPageMember(Member member) {
        Member saved = memberRepository.save(member);
        MyPageMemberResponseDto responseDto = new MyPageMemberResponseDto(saved);
        return ResponseEntity.ok(responseDto);
    }
}
