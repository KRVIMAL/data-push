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
public class TrackingEventRequest {
    @NotBlank(message = "Device ID is required")
    @JsonProperty("device_id")
    private String deviceId;

    @Valid
    @NotNull(message = "Event data is required")
    private TrackingEventDataDTO data;
}