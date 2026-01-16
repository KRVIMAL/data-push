package com.blinkit.iot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatteryDTO {
    @NotNull(message = "Battery percentage is required")
    @Min(value = 0, message = "Battery percentage must be between 0 and 100")
    @Max(value = 100, message = "Battery percentage must be between 0 and 100")
    @JsonProperty("battery_percentage")
    private Float batteryPercentage;

    @NotNull(message = "Charging status is required")
    @Min(value = 0, message = "Charging status must be 0 or 1")
    @Max(value = 1, message = "Charging status must be 0 or 1")
    @JsonProperty("is_charging")
    private Integer isCharging;
}