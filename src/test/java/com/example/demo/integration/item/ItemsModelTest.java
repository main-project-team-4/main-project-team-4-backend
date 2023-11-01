package com.example.demo.integration.item;

import com.example.demo.item.dto.ItemResponseDto;
import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.item.entity.Item;
import com.example.demo.item.entity.SubImage;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.item.service.ItemService;
import com.example.demo.location.dto.CoordinateVo;
import com.example.demo.location.entity.Location;
import com.example.demo.location.entity.MemberLocation;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.trade.type.State;
import com.example.demo.utils.LoadEnvironmentVariables;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URL;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@LoadEnvironmentVariables
public class ItemsModelTest {
    @Autowired
    private ItemService itemService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Retention(RetentionPolicy.RUNTIME)
    @SqlGroup({
            @Sql({
                    "classpath:data/testcase-search.sql"
            }),
            @Sql(
                    scripts = "classpath:truncate-testcases.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            )
    })
    @interface LoadTestCaseSearch {}

    @Retention(RetentionPolicy.RUNTIME)
    @SqlGroup({
            @Sql({
                    "classpath:data/testcase-popular.sql"
            }),
            @Sql(
                    scripts = "classpath:truncate-testcases.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            )
    })
    @interface LoadTestCasePopular {}

    @Retention(RetentionPolicy.RUNTIME)
    @SqlGroup({
            @Sql({
                    "classpath:data/testcase-location.sql"
            }),
            @Sql(
                    scripts = "classpath:truncate-testcases.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            )
    })
    @interface LoadTestCaseLocation {}

    @Retention(RetentionPolicy.RUNTIME)
    @SqlGroup({
            @Sql({
                    "classpath:data/testcase-item.sql"
            }),
            @Sql(
                    scripts = "classpath:truncate-testcases.sql",
                    executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
            )
    })
    @interface LoadTestCaseItem {}

    @LoadTestCaseSearch
    @Test
    @DisplayName("[정상 작동] searchItem - keyword")
    void searchItem_whenGivenKeyword() {
        // given
        String keyword = "cket";
        State[] stateList = new State[]{};
        Pageable pageable = PageRequest.of(0, 10);

        // when
        ResponseEntity<Page<ItemSearchResponseDto>> result = itemService.searchItem(keyword, stateList, pageable);

        // then
        assertThat(result.getBody())
                .hasSize(4)
                .extracting(ItemSearchResponseDto::getItemId)
                .allSatisfy(
                        id -> assertThat(id).isIn(1L, 2L, 3L, 4L)
                );
    }

    @LoadTestCaseSearch
    @Test
    @DisplayName("[정상 작동] searchItem - sort by createdAt")
    void searchItem_whenSortedByCreated() {
        // given
        String keyword = null;
        State[] stateList = new State[]{};
        Pageable pageable = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "createdAt"));

        // when
        ResponseEntity<Page<ItemSearchResponseDto>> result = itemService.searchItem(keyword, stateList, pageable);

        // then
        assertThat(result.getBody().getContent())
                .hasSize(8)
                .extracting(ItemSearchResponseDto::getItemId)
                .isEqualTo(List.of(6L, 5L, 4L, 1L, 2L, 3L, 8L, 7L));
    }

    @LoadTestCaseSearch
    @Test
    @DisplayName("[정상 작동] searchItem - filtered by State")
    void searchItem_whenFilteredByStateList() {
        // given
        String keyword = null;
        State[] stateList = new State[]{State.SELLING, State.RESERVED};
        Pageable pageable = PageRequest.of(0, 8, Sort.by(Sort.Direction.DESC, "createdAt"));

        // when
        ResponseEntity<Page<ItemSearchResponseDto>> result = itemService.searchItem(keyword, stateList, pageable);

        // then
        assertThat(result.getBody().getContent())
                .hasSize(6)
                .extracting(ItemSearchResponseDto::getItemId)
                .isEqualTo(List.of(6L, 5L, 4L, 1L, 2L, 3L));
    }

    @LoadTestCasePopular
    @Test
    @DisplayName("[정상 작동] readPopularItems")
    void readPopularItems() {
        // given
        int num = 4;
        State[] stateList = {};
        Pageable pageable = PageRequest.of(0, num);

        // when
        ResponseEntity<Page<ItemSearchResponseDto>> result = itemService.readPopularItems(stateList, pageable);

        // then
        assertThat(result.getBody())
                .hasSize(num)
                .extracting(ItemSearchResponseDto::getItemId)
                .isEqualTo(List.of(1L, 3L, 5L, 6L));
    }

    @LoadTestCaseLocation
    @Test
    @DisplayName("[정상 작동] readNearbyItems")
    void readNearbyItems() {
        // given
        int num = 7;
        Member member = memberRepository.findById(1L).orElseThrow();
        member.getLocation().setCoordinates(new CoordinateVo(0L, 0L));
        Pageable pageable = PageRequest.of(0, num);

        // when
        ResponseEntity<Page<ItemSearchResponseDto>> result = itemService.readNearbyItems(member, pageable);

        // then
        assertThat(result.getBody())
                .hasSize(num)
                .extracting(ItemSearchResponseDto::getItemId)
                .isEqualTo(List.of(5L, 2L, 6L, 3L, 7L, 4L, 8L));
    }

    @LoadTestCaseItem
    @Test
    @DisplayName("[정상 작동] showItem")
    void showItem() {
        // given
        Long itemId = 1L;

        // when
        ItemResponseDto result = itemService.showItem(itemId);

        // then
        assertThat(result)
                .extracting(ItemResponseDto::getId)
                .isEqualTo(1L);
    }

    @LoadTestCaseItem
    @Test
    @DisplayName("[비정상 작동] showItem - 존재하지 않는 itemId")
    void showItem_whenNonExistedItemId() {
        // given
        Long itemId = 100000L;

        // when
        Executable func = () -> itemService.showItem(itemId);

        // then
        assertThrows(Throwable.class, func);
    }

    @Transactional
    @LoadTestCaseItem
    @Test
    @DisplayName("[정상 작동] updateImage")
    void updateImage() throws IOException {
        // given
        Member member = memberRepository.findById(1L).orElseThrow();
        Long id = 1L;
        MultipartFile new_mainImage = getFixtureAsMockMultipart("1");
        List<MultipartFile> new_subImages = List.of(
                getFixtureAsMockMultipart("2"),
                getFixtureAsMockMultipart("3")
        );
        List<String> old_subImages = List.of(
                "https://cdn.pixabay.com/photo/2023/10/23/13/20/abstract-8336084_640.png",
                "https://cdn.pixabay.com/photo/2023/10/23/13/20/abstract-8336084_640.png",
                "https://cdn.pixabay.com/photo/2023/10/23/13/20/abstract-8336084_640.png"
        );

        // when
        itemService.updateImage(member, id, new_mainImage, old_subImages);

        // then
        Item item = itemRepository.findById(1L).orElseThrow();
        assertThat(item.getSub_images())
                .hasSize(5)
                .allSatisfy(
                        url -> assertThat(url).isNotNull()
                );
    }

    private MockMultipartFile getFixtureAsMockMultipart(String id) throws IOException {
        URL url = new URL("https://cdn.pixabay.com/photo/2023/09/30/09/12/dachshund-8285220_640.jpg");
        return new MockMultipartFile("mock " + id, url.openStream());
    }

}
