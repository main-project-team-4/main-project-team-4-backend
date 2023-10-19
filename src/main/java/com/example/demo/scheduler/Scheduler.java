package com.example.demo.scheduler;

import com.example.demo.item.dto.ItemRankDto;
import com.example.demo.item.entity.Item;
import com.example.demo.item.service.ItemService;
import com.example.demo.repository.RedisRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j(topic = "Scheduler")
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final ItemService itemService;
    private final RedisRepository redisRepository;

    // 초, 분, 시, 일, 주, 월 순서
    @Scheduled(cron = "*/10 * * * * *") // 10초마다 업데이트
    public void updateRank() {
        log.info("랭킹 업데이트 실행");
        List<Item> itemRanking = itemService.updateRanking();


        int rank = 1;
        for (Item item : itemRanking) {
            if (item.getMain_image()!=null) {
                String itemName = item.getName();
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    ItemRankDto response = new ItemRankDto(item);
                    String restaurantRank = objectMapper.writeValueAsString(response);
                    redisRepository.save("item:rank" + rank, restaurantRank);
                    ++rank;
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("객체를 String으로 변환하지 못했습니다.");
                }
            }


        }
    }

}
