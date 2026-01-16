package com.blinkit.iot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceStatusResponse {
    private DeviceStatusData data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceStatusData {
        private String deviceId;
        private String passcode;
        private String masterPasscode;
        private String lockStatus;
        private Long lastEventTimestamp;
    }
}