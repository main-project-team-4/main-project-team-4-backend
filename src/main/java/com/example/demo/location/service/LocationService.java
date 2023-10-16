package com.example.demo.location.service;

import com.example.demo.dto.MessageResponseDto;
import com.example.demo.kakao.properties.KakaoOAuth2Properties;
import com.example.demo.location.dto.CoordinateVo;
import com.example.demo.location.dto.LocationRequestDto;
import com.example.demo.location.entity.MemberLocation;
import com.example.demo.location.repository.MemberLocationRepository;
import com.example.demo.member.entity.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final MemberLocationRepository locationRepository;
    private final RestTemplate restTemplate;
    private final KakaoOAuth2Properties kakaoOAuth2Properties;
    private final ObjectMapper objectMapper;


    public ResponseEntity<MessageResponseDto> createLocation(LocationRequestDto requestDto, Member member) {
        MemberLocation location = new MemberLocation(requestDto, member);
        locationRepository.save(location);
        MessageResponseDto msg = new MessageResponseDto("위치 등록에 성공하였습니다.", 200);
        return ResponseEntity.status(200).body(msg);
    }

    public ResponseEntity<MessageResponseDto> deleteLocation(Long locationId, Member member) {
        MemberLocation location = locationRepository.findById(locationId).orElseThrow(
                () -> new IllegalArgumentException("해당 주소가 존재하지 않습니다.")
        );

        locationRepository.delete(location);
        MessageResponseDto msg = new MessageResponseDto("주소 삭제에 성공하였습니다.", 200);
        return ResponseEntity.status(200).body(msg);
    }

    public CoordinateVo getCoordinateFromAddress(String address) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + kakaoOAuth2Properties.getClientId());

        URI uri = UriComponentsBuilder.fromHttpUrl("https://dapi.kakao.com")
                .path("/v2/local/search/keyword.json")
                .queryParam("size", 1)
                .queryParam("sort", "accuracy")
                .queryParam("query", address)
                .encode().build().toUri();

        RequestEntity<Void> request = RequestEntity
                .get(uri).headers(headers)
                .build();

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        JsonNode jsonNode = objectMapper.readTree(response.getBody());

        if(!CoordinateVo.hasCoordinate(jsonNode)) {
            throw new IllegalArgumentException("해당 주소는 인식할 수 없습니다. 더 정확한 주소를 기입해주세요.");
        }
        return CoordinateVo.fromJsonNode(jsonNode);
    }
}
