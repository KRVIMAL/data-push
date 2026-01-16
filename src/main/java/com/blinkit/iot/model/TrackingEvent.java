package com.blinkit.iot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tracking_events", indexes = {
        @Index(name = "idx_device_id", columnList = "deviceId"),
        @Index(name = "idx_event_timestamp", columnList = "eventTimestamp"),
        @Index(name = "idx_event_type", columnList = "eventType")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TrackingEvent extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String deviceId;

    // Location Data
    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(length = 20)
    private String provider = "GPS";

    // Battery Data
    @Column
    private Float batteryPercentage;

    @Column
    private Integer isCharging; // 1 = charging, 0 = not charging

    // Speed Data
    @Column
    private Double speedValue;

    @Column(length = 10)
    private String speedUnit = "kmph";

    // Lock Data
    @Column(length = 20)
    private String passcode;

    @Column(length = 20, nullable = false)
    private String lockStatus;

    @Column(length = 100)
    private String eventType; // Normal, shackleCut, Device Tampered, etc.

    @Column(nullable = false)
    private Long eventTimestamp; // Unix timestamp in seconds

    @Column(nullable = false)
    private Boolean processed = false;
}