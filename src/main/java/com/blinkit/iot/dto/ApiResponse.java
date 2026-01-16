package com.blinkit.iot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private String message;

    public static ApiResponse success() {
        return new ApiResponse("success");
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(message);
    }
}