package com.example.demo.location.repository;

import com.example.demo.location.entity.MemberLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberLocationRepository extends JpaRepository<MemberLocation, Long> {
    Optional<MemberLocation> findByName(String name);
}
