package com.blinkit.iot.repository;

import com.blinkit.iot.model.TrackingEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingEventRepository extends JpaRepository<TrackingEvent, Long> {
    List<TrackingEvent> findByDeviceIdOrderByEventTimestampDesc(String deviceId);

    Page<TrackingEvent> findByDeviceId(String deviceId, Pageable pageable);

    @Query("SELECT e FROM TrackingEvent e WHERE e.deviceId = :deviceId ORDER BY e.eventTimestamp DESC LIMIT 1")
    TrackingEvent findLatestByDeviceId(String deviceId);

    List<TrackingEvent> findByProcessedFalse();

    @Query("SELECT e FROM TrackingEvent e WHERE e.eventType IS NOT NULL AND e.eventType != 'Normal' ORDER BY e.eventTimestamp DESC")
    List<TrackingEvent> findAllAbnormalEvents();
}