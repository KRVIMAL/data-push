package com.blinkit.iot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.blinkit.iot.dto.ApiResponse;
import com.blinkit.iot.dto.BulkTrackingEventRequest;
import com.blinkit.iot.dto.TrackingEventRequest;
import com.blinkit.iot.service.TrackingEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/ext/tracking/v1")
//@RequiredArgsConstructor
public class TrackingController {

    private final TrackingEventService trackingEventService;

    public TrackingController(TrackingEventService trackingEventService) {
        this.trackingEventService = trackingEventService;
    }

    @PostMapping("/events")
    public ResponseEntity<ApiResponse> receiveTrackingEvent(@Valid @RequestBody TrackingEventRequest request) {
        log.info("Received tracking event for device: {}", request.getDeviceId());

        try {
            trackingEventService.processTrackingEvent(request);
            return ResponseEntity.ok(ApiResponse.success());
        } catch (Exception e) {
            log.error("Error processing tracking event", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to process event"));
        }
    }

    @PostMapping("/events/bulk")
    public ResponseEntity<ApiResponse> receiveBulkTrackingEvents(@Valid @RequestBody BulkTrackingEventRequest request) {
        log.info("Received bulk tracking events, count: {}", request.getData().size());

        try {
            trackingEventService.processBulkTrackingEvents(request);
            return ResponseEntity.ok(ApiResponse.success());
        } catch (Exception e) {
            log.error("Error processing bulk tracking events", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to process bulk events"));
        }
    }
}