package com.example.demo.item.service;

import com.example.demo.category.entity.CategoryM;
import com.example.demo.category.repository.CategoryMRepository;
import com.example.demo.dto.MessageResponseDto;
import com.example.demo.item.dto.ItemResponseDto;
import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.item.dto.itemRequestDto;
import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.member.entity.Member;
import com.example.demo.shop.entity.Shop;
import com.example.demo.shop.repository.ShopRepository;
import com.example.demo.trade.type.State;
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

    private final CategoryMRepository categoryMRepository;
    private final ItemRepository itemRepository;
    private final ShopRepository shopRepository;
    private final S3Uploader s3Uploader;

    public ResponseEntity<ItemResponseDto> createItem(Member member, MultipartFile main_image, List<MultipartFile> sub_images, itemRequestDto requestDto) throws IOException {


        Shop shop = shopRepository.findById(member.getShop().getId()).orElseThrow(
                () -> new IllegalArgumentException("상점을 찾을 수 없습니다"));

        if(!shop.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("해당 상점의 주인만 물건 등록이 가능합니다.");
        }

        // 이미지 S3에 업로드 및 URL 가져오기
        String mainImage = s3Uploader.upload(main_image, "main_image");
        URL main_imageUrl = new URL(mainImage);

        postBlankCheck(main_imageUrl);

        // 다중이미지 S3에 업로드 하기
        List<URL> sub_imageUrls = new ArrayList<>();
        if(sub_images != null && !sub_images.isEmpty()) {
            List<String> subImages = s3Uploader.uploadMultiple(sub_images, "sub_images");

            for (String multipartFile : subImages) {
                sub_imageUrls.add(new URL(multipartFile));
            }
        }

        // 카테고리 설정
        CategoryM categoryM = categoryMRepository.findById(requestDto.getMiddleCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 카테고리는 존재하지 않습니다."));

        Item item = new Item(requestDto.getName(), requestDto.getPrice(), requestDto.getComment(), main_imageUrl, sub_imageUrls, shop, requestDto.getIsContainingDeliveryFee(), categoryM);
        Item saved = itemRepository.save(item);

        ItemResponseDto dto = new ItemResponseDto(saved);
        return ResponseEntity.status(HttpStatus.OK).body(dto);

    }

    private void postBlankCheck(URL imgPaths) {
        if (imgPaths == null) {
            throw new IllegalArgumentException("메인 이미지는 필수입니다.");
        }
    }

    @Transactional
    public ResponseEntity<MessageResponseDto> updateItem(Member member, Long id, MultipartFile new_mainImage, List<MultipartFile> new_subImages, itemRequestDto requestDto) throws IOException {

        Item item = findItem(id);
        Shop shop = item.getShop();
        // 상품을 올린 유저와 수정하려는 유저가 다를 경우
        if (!shop.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("해당 상품을 올린 유저만 상품을 수정 할 수 있습니다.");
        }

        URL mainImageURL = item.getMain_image();

        if(new_mainImage!=null) {
            String filePathInS3 = item.getMain_image().getPath().substring(1);
            s3Uploader.deleteFile(filePathInS3);
            // 새로운 메인 이미지 업로드 및 URL 얻기
            String updatedMainImageUrl = s3Uploader.upload(new_mainImage, "main_image");
            URL updatedMainImageUrlObject = new URL(updatedMainImageUrl);
            mainImageURL = updatedMainImageUrlObject;
        }

//        // 대표 이미지 변경시
//        if(updatedMainImageUrlObject!=null) {
//
//        }

        postBlankCheck(mainImageURL);

        // 대표이미지 안건들면 그대로 원래 있던거 URL 반환하고, 대표 이미지 수정하면 수정한 이미지로 반환하게 file형식말고 URL로

        
        List<URL> old_subImageURLs = item.getSub_images();

        // 새로운 서브 이미지 업로드 및 URL 얻기
        List<URL> new_subImageURLs = new ArrayList<>();

        if(new_subImages != null && !new_subImages.isEmpty()) {
            for (MultipartFile newSubImage : new_subImages) {
                String updatedSubImageUrl = s3Uploader.upload(newSubImage, "sub_images");
                URL updatedSubImageUrlObject = new URL(updatedSubImageUrl);
                new_subImageURLs.add(updatedSubImageUrlObject);
            }
        }

//        for(URL old_subImageURL : old_subImageURLs) {
        for(int i=0; i<old_subImageURLs.size(); i++) {
            if(old_subImageURLs.get(i)==null) {
                old_subImageURLs.remove(i);
            }
        }

//        // 새로운 서브 이미지 업로드 및 URL 얻기
//        List<URL> updatedSubImageUrls = new ArrayList<>();
//        if (new_subImages != null && !new_subImages.isEmpty()) {
//            for (MultipartFile newSubImage : new_subImages) {
//                String updatedSubImageUrl = s3Uploader.upload(newSubImage, "images");
//                URL updatedSubImageUrlObject = new URL(updatedSubImageUrl);
//                updatedSubImageUrls.add(updatedSubImageUrlObject);
//            }
//        } else {
//            // 기존 서브 이미지를 유지하는 경우, 기존 URL을 그대로 사용
//            updatedSubImageUrls.addAll(subImageURLs);
//        }

        // 기존 서브 이미지 목록과 새로운 서브 이미지 목록을 합치기
        List<URL> combinedSubImages = new ArrayList<>(item.getSub_images());
        for(URL image: new_subImageURLs) {
            combinedSubImages.add(image);
        }

        if(combinedSubImages.size()>=6) {
            throw new IllegalArgumentException("사진은 최대 6장까지 올릴 수 있습니다.");
        }

        // 카테고리 설정
        CategoryM categoryM = categoryMRepository.findById(requestDto.getMiddleCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 카테고리는 존재하지 않습니다."));

        // 아이템 업데이트
        //item.update(requestDto.getName(), requestDto.getPrice(), requestDto.getComment(), updatedMainImageUrlObject, combinedSubImages);

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
            State[] stateList,
            Pageable pageable
    ) {
        Page<ItemSearchResponseDto> dtoList = itemRepository.searchBy(keyword, stateList, pageable)
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

    public ResponseEntity<Page<ItemSearchResponseDto>> readPopularItems(State[] stateList, Pageable pageable) {
        Page<ItemSearchResponseDto> dtoList = itemRepository.findPopularItems(stateList, pageable)
                .map(ItemSearchResponseDto::new);
        return ResponseEntity.ok(dtoList);
    }

    @Transactional
    public ResponseEntity<Page<ItemSearchResponseDto>> readNearbyItems(Member member, Pageable pageable) {
        if(member.getLocation() == null) {
            return ResponseEntity.ok(Page.empty());
        }

        Page<ItemSearchResponseDto> dtoList = itemRepository.findNearbyItems(member.getLocation(), member, pageable)
                .map(ItemSearchResponseDto::new);
        return ResponseEntity.ok(dtoList);
    }

    @Transactional
    public ResponseEntity<Page<ItemSearchResponseDto>> readItemsOfShop(Long shopId, Long[] exclude, Pageable pageable) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상점은 존재하지 않습니다."));

        Page<ItemSearchResponseDto> dtoList = itemRepository.findInShop(shop.getMember(), exclude, pageable)
                .map(ItemSearchResponseDto::new);
        return ResponseEntity.ok(dtoList);
    }
}
