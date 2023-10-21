package com.example.demo.item.service;

import com.example.demo.dto.MessageResponseDto;
import com.example.demo.item.dto.ItemResponseDto;
import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.item.dto.itemRequestDto;
import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.location.entity.Location;
import com.example.demo.member.entity.Member;
import com.example.demo.shop.entity.Shop;
import com.example.demo.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ShopRepository shopRepository;
    private final S3Uploader s3Uploader;

    public ResponseEntity<MessageResponseDto> createItem(Member member, MultipartFile main_image, List<MultipartFile> sub_images, itemRequestDto requestDto) throws IOException {
        postBlankCheck(main_image);

        Shop shop = shopRepository.findById(member.getShop().getId()).orElseThrow(
                () -> new IllegalArgumentException("상점을 찾을 수 없습니다"));

        if(!shop.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("해당 상점의 주인만 물건 등록이 가능합니다.");
        }

        // 이미지 S3에 업로드 및 URL 가져오기
        String mainImage = s3Uploader.upload(main_image, "main_image");
        URL main_imageUrl = new URL(mainImage);

        // 다중이미지 S3에 업로드 하기

        List<String> subImages = s3Uploader.uploadMultiple(sub_images, "sub_images");
        List<URL> sub_imageUrls = new ArrayList<>();

        for (String multipartFile : subImages) {
            sub_imageUrls.add(new URL(multipartFile));
        }

        itemRepository.save(new Item(requestDto.getName(), requestDto.getPrice(), requestDto.getComment(), main_imageUrl, sub_imageUrls, shop, requestDto.getIsContainingDeliveryFee()));

        MessageResponseDto msg = new MessageResponseDto("상품이 등록되었습니다.", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(msg);

    }

    private void postBlankCheck(MultipartFile imgPaths) {
        if (imgPaths == null) {
            throw new IllegalArgumentException("메인 이미지는 필수입니다.");
        }
    }

    @Transactional
    public ResponseEntity<MessageResponseDto> updateItem(Member member, Long id, MultipartFile new_mainImage, List<MultipartFile> new_subImages, itemRequestDto requestDto) throws IOException {
        postBlankCheck(new_mainImage);

        Item item = findItem(id);
        Shop shop = item.getShop();

        // 상품을 올린 유저와 수정하려는 유저가 다를 경우
        if (!shop.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("해당 상품을 올린 유저만 상품을 수정 할 수 있습니다.");
        }

        // 새로운 메인 이미지 업로드 및 URL 얻기
        String updatedMainImageUrl = s3Uploader.upload(new_mainImage, "images");
        URL updatedMainImageUrlObject = new URL(updatedMainImageUrl);

        // 새로운 서브 이미지 업로드 및 URL 얻기
        List<URL> updatedSubImageUrls = new ArrayList<>();
        if (new_subImages != null && !new_subImages.isEmpty()) {
            for (MultipartFile newSubImage : new_subImages) {
                String updatedSubImageUrl = s3Uploader.upload(newSubImage, "images");
                URL updatedSubImageUrlObject = new URL(updatedSubImageUrl);
                updatedSubImageUrls.add(updatedSubImageUrlObject);
            }
        }

        // 기존 서브 이미지 목록과 새로운 서브 이미지 목록을 합치기
        List<URL> combinedSubImages = new ArrayList<>(item.getSub_images());
        if(combinedSubImages.size()>=6) {
            throw new IllegalArgumentException("사진은 최대 6장까지 올릴 수 있습니다.");
        }
        combinedSubImages.addAll(updatedSubImageUrls);

        // 아이템 업데이트
        item.update(requestDto.getName(), requestDto.getPrice(), requestDto.getComment(), updatedMainImageUrlObject, combinedSubImages);

        MessageResponseDto msg = new MessageResponseDto("상품이 수정되었습니다.", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }


    private Item findItem(Long id) {
        return itemRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 상품은 존재하지 않습니다."));
    }

    public ResponseEntity<MessageResponseDto> deleteItem(Member member, Long id) {
        Item item = findItem(id);
        Shop shop = item.getShop();

        // 상품을 올린 유저와 삭제하려는 유저가 다를 경우
        if(!shop.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("해당 상품을 올린 유저만 상품을 수정 할 수 있습니다.");
        }

        String filePathInS3 = item.getMain_image().getPath().substring(1);
        s3Uploader.deleteFile(filePathInS3);
        itemRepository.delete(item);

        MessageResponseDto msg = new MessageResponseDto("상품이 삭제되었습니다.", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    @Transactional
    public ResponseEntity<Page<ItemSearchResponseDto>> searchItem(
            String keyword,
            Pageable pageable
    ) {
        Page<ItemSearchResponseDto> dtoList = itemRepository.searchBy(keyword, pageable)
                .map(ItemSearchResponseDto::new);
        return ResponseEntity.ok(dtoList);
    }

    // 랭킹 Top 20 조회
    public List<Item> updateRanking() {
        return itemRepository.findTop100ByOrderByWishCountDesc();
    }

    @Transactional
    public ItemResponseDto showItem(Long id) {
        Item item = findItem(id);
        return new ItemResponseDto(item);
    }

    public ResponseEntity<Page<ItemSearchResponseDto>> readPopularItems(Pageable pageable) {
        Page<ItemSearchResponseDto> dtoList = itemRepository.findPopularItems(pageable)
                .map(ItemSearchResponseDto::new);
        return ResponseEntity.ok(dtoList);
    }

    @Transactional
    public ResponseEntity<Page<ItemSearchResponseDto>> readNearbyItems(Location location, Pageable pageable) {
        Page<ItemSearchResponseDto> dtoList = itemRepository.findNearbyItems(location, pageable)
                .map(ItemSearchResponseDto::new);
        return ResponseEntity.ok(dtoList);
    }

    @Transactional
    public ResponseEntity<Page<ItemSearchResponseDto>> readItemsOfShop(Long shopId, Pageable pageable) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상점은 존재하지 않습니다."));

        Page<ItemSearchResponseDto> dtoList = itemRepository.findInShop(shop.getMember(), pageable)
                .map(ItemSearchResponseDto::new);
        return ResponseEntity.ok(dtoList);
    }
}
