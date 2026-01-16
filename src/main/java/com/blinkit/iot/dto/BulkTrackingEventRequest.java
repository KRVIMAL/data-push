package com.blinkit.iot.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkTrackingEventRequest {
    @Valid
    @NotEmpty(message = "Data array cannot be empty")
    private List<BulkEventItemDTO> data;
}