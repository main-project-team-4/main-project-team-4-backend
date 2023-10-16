package com.example.demo.unit.location.service;

import com.example.demo.location.dto.CoordinateVo;
import com.example.demo.location.service.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:.env")
class LocationServiceTest {
    @Autowired
    private LocationService locationService;

    @Test
    @DisplayName("[정상 작동] 위도, 경도 검색 API 작동 여부 확인")
    void getCoordinate() throws JsonProcessingException {
        // given
        String address = "카카오 본사";

        // when
        CoordinateVo coordinate = locationService.getCoordinateFromAddress(address);

        // then
        Long latitude = coordinate.Latitude();
        Long longitude = coordinate.Longitude();

        assertNotNull(latitude);
        assertNotNull(longitude);
    }

    @Test
    @DisplayName("[비정상 작동] 이상한 주소 입력 시, 오류 발생.")
    void getCoordinateOccurErrorWhenWrongAddress() {
        // given
        String address = "절대로 존재할 수 없는 주소 문자열 조합";

        // when
        Executable func = () -> locationService.getCoordinateFromAddress(address);

        // then
        assertThrows(IllegalArgumentException.class, func);
    }
}