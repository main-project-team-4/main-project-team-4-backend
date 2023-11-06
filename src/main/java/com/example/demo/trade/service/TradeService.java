package com.example.demo.trade.service;

import com.example.demo.dto.MessageResponseDto;
import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.shop.entity.Shop;
import com.example.demo.trade.dto.ItemStateRequestDto;
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

import org.springframework.security.access.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final ItemRepository itemRepository;
    private final TradeRepository tradeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseEntity<Page<ItemSearchResponseDto>> readOrders(Member member, State[] stateList, Pageable pageable) {
        Page<ItemSearchResponseDto> dtoPage = itemRepository.findPurchaseItemByMember_id(member.getId(), stateList, pageable)
                .map(ItemSearchResponseDto::new);
        return ResponseEntity.ok(dtoPage);
    }

    @Transactional
    public ResponseEntity<Page<ItemSearchResponseDto>> readSales(Member member, State[] stateList, Pageable pageable) {
        Page<ItemSearchResponseDto> dtoPage = itemRepository.findItemByMember_IdAndStateList(member.getId(), stateList, pageable)
                .map(ItemSearchResponseDto::new);
        return ResponseEntity.ok(dtoPage);
    }

    @Transactional
    public ResponseEntity<MessageResponseDto> updateItemState(Member member, ItemStateRequestDto requestDto) throws AccessDeniedException {
        Item item = itemRepository.findById(requestDto.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 상품은 존재하지 않습니다."));

        if(!validateOwnerOfItem(member, item)) {
            throw new AccessDeniedException("거래 상태 변화는 상품의 소유자만 변경할 수 있습니다.");
        }

        State state = requestDto.getState();

        // Item 상태 변경.
        item.setState(state);

        MessageResponseDto msg = new MessageResponseDto("거래 상태 변화에 성공했습니다.", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    @Transactional
    public ResponseEntity<MessageResponseDto> updateTradeRecord(Member member, TradeRequestDto tradeRequestDto, Long itemId) throws AccessDeniedException {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 상품은 존재하지 않습니다."));

        if(!validateOwnerOfItem(member, item)) {
            throw new AccessDeniedException("거래 상태 변화는 상품의 소유자만 변경할 수 있습니다.");
        }

        Member consumer = memberRepository.findById(tradeRequestDto.getConsumerId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 회원은 존재하지 않습니다."));

        State state = State.SOLDOUT;

        // 있으면 거래 기록 치환.
        // 없으면 거래 기록 남기기.
        tradeRepository.findByItem_Id(
                itemId
        ).ifPresentOrElse(
                trade -> updateTrade(trade, consumer, state),
                () -> saveTrade(consumer, item, state)
        );

        MessageResponseDto msg = new MessageResponseDto("거래 기록에 성공했습니다.", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }

    private void updateTrade(Trade trade, Member consumer, State state) {
        trade.setMember(consumer);
        trade.getItem().setState(state);
    }

    private void saveTrade(Member consumer, Item item, State state) {
        Trade trade = new Trade(consumer, item, state);
        tradeRepository.save(trade);
    }

    private boolean validateOwnerOfItem(Member memberWhoAccessToTrade, Item item) {
        Shop shop = item.getShop();
        Member seller = shop.getMember();

        Long sellerId = seller.getId();
        Long memberIdWhoAccessToTradeId = memberWhoAccessToTrade.getId();
        return sellerId.equals(memberIdWhoAccessToTradeId);
    }
}
