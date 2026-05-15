package com.example.repository;

import com.example.entity.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {
    List<Tracking> findByOrderId(Long orderId);
}
