package com.example.demo.location.controller;


import com.example.demo.dto.MessageResponseDto;
import com.example.demo.item.dto.ItemSearchResponseDto;
import com.example.demo.item.service.ItemService;
import com.example.demo.location.dto.LocationRequestDto;
import com.example.demo.location.entity.Location;
import com.example.demo.location.service.LocationService;
import com.example.demo.member.entity.Member;
import com.example.demo.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LocationController implements LocationDocs{
    private final LocationService locationService;
    private final ItemService itemService;

    @PostMapping("/locations")
    public ResponseEntity<MessageResponseDto> createLocation(@RequestBody @Valid LocationRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return locationService.createLocation(requestDto, userDetails.getMember());
    }
    @DeleteMapping("/locations/{location_id}")
    public ResponseEntity<MessageResponseDto> deleteLocation(@PathVariable Long location_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return locationService.deleteLocation(location_id, userDetails.getMember());
    }

    @GetMapping("/api/nearby-items")
    public ResponseEntity<Page<ItemSearchResponseDto>> readNearbyItems(
            @AuthenticationPrincipal UserDetailsImpl principal,
            @PageableDefault Pageable pageable
    ) {
        Member member = principal.getMember();
        Location trgLocation = member.getLocation();
        return itemService.readNearbyItems(trgLocation, pageable);
    }
}




