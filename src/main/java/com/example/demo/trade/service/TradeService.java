package com.example.demo.trade.service;

import com.example.demo.dto.MessageResponseDto;
import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.shop.entity.Shop;
import com.example.demo.trade.dto.TradeRequestDto;
import com.example.demo.trade.entity.Trade;
import com.example.demo.trade.repository.TradeRepository;
import com.example.demo.trade.type.State;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final ItemRepository itemRepository;
    private final TradeRepository tradeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseEntity<Page<ItemSearchResponseDto>> readOrders(Member member, Pageable pageable) {
        Page<ItemSearchResponseDto> dtoPage = itemRepository.findPurchaseItemByMember_id(member.getId(), pageable)
                .map(ItemSearchResponseDto::new);
        return ResponseEntity.ok(dtoPage);
    }

    @Transactional
    public ResponseEntity<Page<ItemSearchResponseDto>> readSales(Member member, Pageable pageable) {
        Page<ItemSearchResponseDto> dtoPage = itemRepository.findSellingItemByMember_id(member.getId(), pageable)
                .map(ItemSearchResponseDto::new);
        return ResponseEntity.ok(dtoPage);
    }

    @Transactional
    public ResponseEntity<MessageResponseDto> updateTradeRecord(Member member, TradeRequestDto tradeRequestDto) throws AccessDeniedException {
        Item item = itemRepository.findById(tradeRequestDto.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 상품은 존재하지 않습니다."));

        if(!validateOwnerOfItem(member, item)) {
            throw new AccessDeniedException("거래 상태 변화는 상품의 소유자만 변경할 수 있습니다.");
        }

        Member consumer = memberRepository.findById(tradeRequestDto.getConsumerId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 회원은 존재하지 않습니다."));

        State state = tradeRequestDto.getState();

        // 없으면 거래 기록 남기기.
        tradeRepository.findByMember_IdAndItem_Id(
                tradeRequestDto.getConsumerId(),
                tradeRequestDto.getItemId()
        ).orElseGet(() -> saveTrade(consumer, item, state));

        // Item 상태 변경.
        item.setState(state);

        MessageResponseDto msg = new MessageResponseDto("회원탈퇴에 성공하였습니다.", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    private Trade saveTrade(Member consumer, Item item, State state) {
        Trade trade = new Trade(consumer, item, state);
        return tradeRepository.save(trade);
    }

    private boolean validateOwnerOfItem(Member memberWhoAccessToTrade, Item item) {
        return Optional.of(item.getShop())
                .map(Shop::getMember)
                .map(Member::getId)
                .filter(memberIdWhoOwnItem -> memberWhoAccessToTrade.getId().equals(memberIdWhoOwnItem))
                .isPresent();
    }
}
