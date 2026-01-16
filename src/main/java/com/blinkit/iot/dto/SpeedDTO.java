package com.blinkit.iot.dto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeedDTO {
    @NotNull(message = "Speed value is required")
    private Double value;

    private String unit = "kmph";
}