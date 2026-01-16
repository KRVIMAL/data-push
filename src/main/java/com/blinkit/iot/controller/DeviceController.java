package com.blinkit.iot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.blinkit.iot.dto.DeviceStatusResponse;
import com.blinkit.iot.model.TrackingEvent;
import com.blinkit.iot.service.DeviceService;
import com.blinkit.iot.service.TrackingEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ext/devices/v1")
public class DeviceController {

    private final DeviceService deviceService;
    private final TrackingEventService trackingEventService;

    public DeviceController(DeviceService deviceService, TrackingEventService trackingEventService) {
        this.deviceService = deviceService;
        this.trackingEventService = trackingEventService;
    }

    @GetMapping("/{deviceId}/status")
    public ResponseEntity<DeviceStatusResponse> getDeviceStatus(@PathVariable String deviceId) {
        log.info("Fetching status for device: {}", deviceId);
        DeviceStatusResponse response = deviceService.getDeviceStatus(deviceId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{deviceId}/history")
    public ResponseEntity<List<TrackingEvent>> getDeviceHistory(@PathVariable String deviceId) {
        log.info("Fetching history for device: {}", deviceId);
        List<TrackingEvent> events = trackingEventService.getDeviceHistory(deviceId);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/abnormal")
    public ResponseEntity<List<TrackingEvent>> getAbnormalEvents() {
        log.info("Fetching all abnormal events");
        List<TrackingEvent> events = trackingEventService.getAbnormalEvents();
        return ResponseEntity.ok(events);
    }
}