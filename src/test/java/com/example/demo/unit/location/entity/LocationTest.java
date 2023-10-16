package com.example.demo.unit.location.entity;

import com.example.demo.location.entity.ItemLocation;
import com.example.demo.location.entity.MemberLocation;
import com.example.demo.location.repository.ItemLocationRepository;
import com.example.demo.location.repository.MemberLocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:.env")
class LocationTest {
    @Autowired
    private MemberLocationRepository memberLocationRepository;
    @Autowired
    private ItemLocationRepository itemLocationRepository;

    @Transactional
    @Test
    @DisplayName("[정상 작동] MemberLocation 엔티티 영속 시, LocationListener 작동 여부 확인.")
    void loadCoordinatesWithMemberLocation() {
        // given
        String address = "카카오 본사";
        MemberLocation entity = new MemberLocation();
        entity.setName(address);

        // when
        MemberLocation saved = memberLocationRepository.save(entity);

        // then
        log.info(saved.toString());
        assertTrue(saved.hasCoordinates());
    }

    @Transactional
    @Test
    @DisplayName("[비정상 작동] MemberLocation 엔티티 영속 시, 잘못된 주소를 기입하면 오류 발생.")
    void loadCoordinatesWithMemberLocationOccurWhenWrongAddress() {
        // given
        String address = "절대로 존재할 수 없는 주소 문자열 조합";
        MemberLocation entity = new MemberLocation();
        entity.setName(address);

        // when
        Executable func = () -> memberLocationRepository.save(entity);

        // then
        assertThrows(InvalidDataAccessApiUsageException.class, func);
    }

    @Transactional
    @Test
    @DisplayName("[정상 작동] ItemLocation 엔티티 영속 시, LocationListener 작동 여부 확인.")
    void loadCoordinatesWithItemLocation() {
        // given
        String address = "카카오 본사";
        ItemLocation entity = new ItemLocation();
        entity.setName(address);

        // when
        ItemLocation saved = itemLocationRepository.save(entity);

        // then
        log.info(saved.toString());
        assertTrue(saved.hasCoordinates());
    }

    @Transactional
    @Test
    @DisplayName("[비정상 작동] ItemLocation 엔티티 영속 시, 잘못된 주소를 기입하면 오류 발생.")
    void loadCoordinatesWithItemLocationOccurWhenWrongAddress() {
        // given
        String address = "절대로 존재할 수 없는 주소 문자열 조합";
        ItemLocation entity = new ItemLocation();
        entity.setName(address);

        // when
        Executable func = () -> itemLocationRepository.save(entity);

        // then
        assertThrows(InvalidDataAccessApiUsageException.class, func);
    }
}