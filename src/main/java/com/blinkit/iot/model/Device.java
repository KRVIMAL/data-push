package com.blinkit.iot.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "devices", indexes = {
        @Index(name = "idx_device_id", columnList = "deviceId"),
        @Index(name = "idx_mac_id", columnList = "macId")
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Device extends BaseEntity {

    @Column(nullable = false, unique = true, length = 100)
    private String deviceId;

    @Column(unique = true, length = 100)
    private String macId;

    @Column(length = 200)
    private String qrCode;

    @Column(length = 20)
    private String currentPasscode;

    @Column(length = 20)
    private String masterPasscode;

    @Column(nullable = false)
    private LocalDateTime masterPasscodeUpdatedAt;

    @Column(length = 20)
    private String lockStatus = "unlocked"; // locked/unlocked

    @Column
    private LocalDateTime lastEventTimestamp;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(length = 500)
    private String remarks;
}