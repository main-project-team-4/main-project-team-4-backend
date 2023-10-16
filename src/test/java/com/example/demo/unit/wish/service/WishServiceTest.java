package com.example.demo.unit.wish.service;

import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.member.entity.Member;
import com.example.demo.wish.entity.Wish;
import com.example.demo.wish.repository.WishRepository;
import com.example.demo.wish.service.WishService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WishServiceTest {
    private final ItemRepository itemRepository = mock(ItemRepository.class);
    private final WishRepository wishRepository = mock(WishRepository.class);

    private final WishService wishService = new WishService(itemRepository, wishRepository);

    @Test
    @DisplayName("[정상 작동] toggle 작동 - 찜 기록 없는 아이템 찜.")
    void toggleWhenNonExistWish() {
        // given
        Member member = mock(Member.class);
        Long itemId = 1L;
        Item item = mock(Item.class);

        when(wishRepository.findByMember_IdAndItem_Id(any(), any()))
                .thenReturn(Optional.empty());
        when(itemRepository.findById(any()))
                .thenReturn(Optional.of(item));

        // when
        wishService.toggle(member, itemId);

        // then
        verify(wishRepository, times(1)).save(any());

    }

    @Test
    @DisplayName("[정상 작동] toggle 작동 - 찜 기록 있는 아이템 찜 삭제.")
    void toggleWhenExistWish() {
        // given
        Wish wish = mock(Wish.class);
        Member member = mock(Member.class);
        Long itemId = 1L;

        when(wishRepository.findByMember_IdAndItem_Id(any(), any()))
                .thenReturn(Optional.of(wish));

        // when
        wishService.toggle(member, itemId);

        // then
        verify(wishRepository, times(1)).delete(any());

    }

    @Test
    @DisplayName("[비정상 작동] 존재하지 않는 찜에 대한 저장.")
    void toggleOccurErrorWhenNonExistedItem() {
        // given
        Member member = mock(Member.class);
        Long itemId = 1L;

        when(wishRepository.findByMember_IdAndItem_Id(any(), any()))
                .thenReturn(Optional.empty());
        when(itemRepository.findById(any()))
                .thenReturn(Optional.empty());

        // when
        Executable func = () -> wishService.toggle(member, itemId);

        // then
        assertThrows(IllegalArgumentException.class, func);

    }
}