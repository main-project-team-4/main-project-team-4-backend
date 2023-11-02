package com.example.demo.item.service;

import com.example.demo.category.entity.CategoryM;
import com.example.demo.category.repository.CategoryMRepository;
import com.example.demo.dto.MessageResponseDto;
import com.example.demo.item.dto.ImageResponseDto;
import com.example.demo.item.dto.ItemRequestDto;
import com.example.demo.item.dto.ItemResponseDto;
import com.example.demo.item.dto.ItemSearchResponseDto;
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

    public ResponseEntity<ItemResponseDto> createItem(Member member, MultipartFile main_image, List<MultipartFile> sub_images, ItemRequestDto requestDto) throws IOException {


        Shop shop = shopRepository.findById(member.getShop().getId()).orElseThrow(
                () -> new IllegalArgumentException("상점을 찾을 수 없습니다"));

        if (!shop.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("해당 상점의 주인만 물건 등록이 가능합니다.");
        }

        // 이미지 S3에 업로드 및 URL 가져오기
        String mainImage = s3Uploader.upload(main_image, "main_image");
        URL main_imageUrl = new URL(mainImage);

        postBlankCheck(main_imageUrl);

        // 다중이미지 S3에 업로드 하기
        List<URL> sub_imageUrls = new ArrayList<>();
        if (sub_images != null && !sub_images.isEmpty()) {
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

    public void updateImage(Member member, Long id, MultipartFile new_mainImage, List<String> old_subImages) throws IOException {

        Item item = findItem(id);
        Shop shop = item.getShop();

        if (!shop.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("해당 상품을 올린 유저만 상품을 수정 할 수 있습니다.");
        }

        URL mainImageURL = item.getMain_image();

        if (new_mainImage != null) {
            String filePathInS3 = item.getMain_image().getPath().substring(1);
            s3Uploader.deleteFile(filePathInS3);
            // 새로운 메인 이미지 업로드 및 URL 얻기
            String updatedMainImageUrl = s3Uploader.upload(new_mainImage, "main_image");
            URL updatedMainImageUrlObject = new URL(updatedMainImageUrl);
            mainImageURL = updatedMainImageUrlObject;
            item.updateMainImage(mainImageURL);
        }

        postBlankCheck(item.getMain_image());

        // 새 이미지 S3에 업로드 + DB에 S3 주소(URL) 저장
        item.getSubImageList().clear();
        item.addAllSubImagesString(old_subImages);

//        List<String> new_subImagesURLs = s3Uploader.uploadMultiple(new_subImages, "sub_images");
//        item.addAllSubImages(new_subImagesURLs);

        itemRepository.save(item);
    }

    public ImageResponseDto imageOrdering(List<MultipartFile> new_subImages) throws IOException {
//        List<URL> sub_images = new ArrayList<>();
        List<String> new_subImagesString = new ArrayList<>();
        if(new_subImages != null) {
            new_subImagesString = s3Uploader.uploadMultiple(new_subImages, "sub_images");
//            for(String image : new_subImagesURLs) {
//                sub_images.add(new URL(image));
//            }
        }
        return new ImageResponseDto(new_subImagesString);
    }




    @Transactional
    public ResponseEntity<MessageResponseDto> updateItem(Member member, Long id, ItemRequestDto requestDto) throws IOException {

        Item item = findItem(id);
        Shop shop = item.getShop();
        // 상품을 올린 유저와 수정하려는 유저가 다를 경우
        if (!shop.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("해당 상품을 올린 유저만 상품을 수정 할 수 있습니다.");
        }

        // 카테고리 설정
        CategoryM categoryM = categoryMRepository.findById(requestDto.getMiddleCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 카테고리는 존재하지 않습니다."));

        // 아이템 업데이트
        item.update(requestDto.getName(), requestDto.getPrice(), requestDto.getComment(), requestDto.getIsContainingDeliveryFee(), categoryM);

        itemRepository.save(item);
        MessageResponseDto msg = new MessageResponseDto("상품이 수정되었습니다.", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(msg);
        }


        // 상품 찾기
        private Item findItem (Long id){
            return itemRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("선택한 상품은 존재하지 않습니다."));
        }

        public ResponseEntity<MessageResponseDto> deleteItem (Member member, Long id){
            Item item = findItem(id);
            Shop shop = item.getShop();

            // 상품을 올린 유저와 삭제하려는 유저가 다를 경우
            if (!shop.getMember().getId().equals(member.getId())) {
                throw new IllegalArgumentException("해당 상품을 올린 유저만 상품을 수정 할 수 있습니다.");
            }

            String filePathInS3 = item.getMain_image().getPath().substring(1);
            s3Uploader.deleteFile(filePathInS3);

            itemRepository.delete(item);
            MessageResponseDto msg = new MessageResponseDto("상품이 삭제되었습니다.", HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(msg);
        }

        @Transactional
        public ResponseEntity<Page<ItemSearchResponseDto>> searchItem (
                String keyword,
                State[]stateList,
                Pageable pageable
    ){
            Page<ItemSearchResponseDto> dtoList = itemRepository.searchBy(keyword, stateList, pageable)
                    .map(ItemSearchResponseDto::new);
            return ResponseEntity.ok(dtoList);
        }

        // 랭킹 Top 20 조회
        public List<Item> updateRanking () {
            return itemRepository.findTop100ByOrderByWishCountDesc();
        }

        @Transactional
        public ItemResponseDto showItem (Long id){
            Item item = findItem(id);
            return new ItemResponseDto(item);
        }

        public ResponseEntity<Page<ItemSearchResponseDto>> readPopularItems (State[]stateList, Pageable pageable){
            Page<ItemSearchResponseDto> dtoList = itemRepository.findPopularItems(stateList, pageable)
                    .map(ItemSearchResponseDto::new);
            return ResponseEntity.ok(dtoList);
        }

        @Transactional
        public ResponseEntity<Page<ItemSearchResponseDto>> readNearbyItems (Member member, Pageable pageable){
            if (member.getLocation() == null) {
                return ResponseEntity.ok(Page.empty());
            }

            Page<ItemSearchResponseDto> dtoList = itemRepository.findNearbyItems(member.getLocation(), member, pageable)
                    .map(ItemSearchResponseDto::new);
            return ResponseEntity.ok(dtoList);
        }

        @Transactional
        public ResponseEntity<Page<ItemSearchResponseDto>> readItemsOfShop (Long shopId, Long[]exclude, Pageable
        pageable){
            Shop shop = shopRepository.findById(shopId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 상점은 존재하지 않습니다."));

            Page<ItemSearchResponseDto> dtoList = itemRepository.findInShop(shop.getMember(), exclude, pageable)
                    .map(ItemSearchResponseDto::new);
            return ResponseEntity.ok(dtoList);
        }


}



