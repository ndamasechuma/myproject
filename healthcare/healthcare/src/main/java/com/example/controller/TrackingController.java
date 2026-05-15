package com.example.controller;

import com.example.entity.Tracking;
import com.example.repository.TrackingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracking")
public class TrackingController {
    private final TrackingRepository trackingRepository;

    public TrackingController(TrackingRepository trackingRepository) {
        this.trackingRepository = trackingRepository;
    }

    @PostMapping
    public Tracking addTracking(@RequestBody Tracking tracking) {
        return trackingRepository.save(tracking);
    }

    @GetMapping("/{orderId}")
    public List<Tracking> getTrackingByOrder(@PathVariable Long orderId) {
        return trackingRepository.findByOrderId(orderId);
    }
}
