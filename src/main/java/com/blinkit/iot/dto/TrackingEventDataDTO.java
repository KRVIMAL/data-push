package com.blinkit.iot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackingEventDataDTO {
    @Valid
    @NotNull(message = "Location is required")
    private LocationDTO location;

    @Valid
    @NotNull(message = "Battery information is required")
    private BatteryDTO battery;

    @Valid
    @NotNull(message = "Speed information is required")
    private SpeedDTO speed;

    @NotBlank(message = "Passcode is required")
    private String passcode;

    @NotBlank(message = "Lock status is required")
    @JsonProperty("lock_status")
    private String lockStatus;

    @JsonProperty("event_type")
    private String eventType;

    @NotNull(message = "Timestamp is required")
    private Long timestamp;
}