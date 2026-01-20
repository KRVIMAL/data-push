package com.blinkit.iot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.blinkit.iot.dto.BulkEventItemDTO;
import com.blinkit.iot.dto.BulkTrackingEventRequest;
import com.blinkit.iot.dto.TrackingEventRequest;
import com.blinkit.iot.model.TrackingEvent;
import com.blinkit.iot.repository.TrackingEventRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackingEventService {

    private final TrackingEventRepository trackingEventRepository;
    private final DeviceService deviceService;

    @Transactional
    public void processTrackingEvent(TrackingEventRequest request) {
//        log.debug("Processing tracking event for device: {}", request.getDeviceId());

        TrackingEvent event = new TrackingEvent();
        event.setDeviceId(request.getDeviceId());
        event.setLatitude(request.getLocation().getLat());
        event.setLongitude(request.getLocation().getLon());
        event.setProvider(request.getLocation().getProvider());
        event.setBatteryPercentage(request.getBattery().getBatteryPercentage());
        event.setIsCharging(request.getBattery().getIsCharging());
        event.setSpeedValue(request.getSpeed().getValue());
        event.setSpeedUnit(request.getSpeed().getUnit());
        event.setPasscode(request.getPasscode());
        event.setLockStatus(request.getLockStatus());
        event.setEventType(request.getEventType() != null ? request.getEventType() : "Normal");
        event.setEventTimestamp(request.getTimestamp());
        event.setProcessed(false);

        trackingEventRepository.save(event);

        deviceService.updateDeviceStatus(
                request.getDeviceId(),
                request.getPasscode(),
                request.getLockStatus(),
                request.getTimestamp()
        );

        log.info("Tracking event saved for device: {} with event type: {}",
                request.getDeviceId(), event.getEventType());

        if (event.getEventType() != null && !event.getEventType().equals("Normal")) {
            processAbnormalEvent(event);
        }
    }

    @Transactional
    public void processBulkTrackingEvents(BulkTrackingEventRequest request) {
//        log.debug("Processing bulk tracking events, count: {}", request.getData().size());

        List<TrackingEvent> events = new ArrayList<>();

        for (BulkEventItemDTO item : request.getData()) {
            TrackingEvent event = new TrackingEvent();
            event.setDeviceId(item.getDeviceId());
            event.setLatitude(item.getLocation().getLat());
            event.setLongitude(item.getLocation().getLon());
            event.setProvider(item.getLocation().getProvider());
            event.setBatteryPercentage(item.getBattery().getBatteryPercentage());
            event.setIsCharging(item.getBattery().getIsCharging());
            event.setSpeedValue(item.getSpeed().getValue());
            event.setSpeedUnit(item.getSpeed().getUnit());
            event.setPasscode(item.getPasscode());
            event.setLockStatus(item.getLockStatus());
            event.setEventType(item.getEventType() != null ? item.getEventType() : "Normal");
            event.setEventTimestamp(item.getTimestamp());
            event.setProcessed(false);

            events.add(event);

            // Update device status for each event
            deviceService.updateDeviceStatus(
                    item.getDeviceId(),
                    item.getPasscode(),
                    item.getLockStatus(),
                    item.getTimestamp()
            );
        }

        trackingEventRepository.saveAll(events);
        log.info("Bulk tracking events saved, count: {}", events.size());
    }

    @Async
    public void processAbnormalEvent(TrackingEvent event) {
        log.warn("Abnormal event detected - Device: {}, Type: {}, Location: ({}, {})",
                event.getDeviceId(), event.getEventType(),
                event.getLatitude(), event.getLongitude());

        // Add your custom logic here:
        // - Send alerts
        // - Trigger notifications
        // - Update monitoring dashboards
        // - Log to security systems
    }

    @Transactional(readOnly = true)
    public List<TrackingEvent> getDeviceHistory(String deviceId) {
        return trackingEventRepository.findByDeviceIdOrderByEventTimestampDesc(deviceId);
    }

    @Transactional(readOnly = true)
    public List<TrackingEvent> getAbnormalEvents() {
        return trackingEventRepository.findAllAbnormalEvents();
    }
}