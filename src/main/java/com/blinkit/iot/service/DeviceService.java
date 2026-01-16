package com.blinkit.iot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.blinkit.iot.dto.DeviceStatusResponse;
import com.blinkit.iot.exception.ResourceNotFoundException;
import com.blinkit.iot.model.Device;
import com.blinkit.iot.repository.DeviceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
//@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Transactional(readOnly = true)
    public Device getDeviceByDeviceId(String deviceId) {
        return deviceRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found: " + deviceId));
    }

    @Transactional
    public Device updateDeviceStatus(String deviceId, String passcode, String lockStatus, Long timestamp) {
        Device device = deviceRepository.findByDeviceId(deviceId)
                .orElseGet(() -> {
                    log.info("Creating new device: {}", deviceId);
                    Device newDevice = new Device();
                    newDevice.setDeviceId(deviceId);
                    newDevice.setIsActive(true);
                    newDevice.setMasterPasscodeUpdatedAt(LocalDateTime.now());
                    return newDevice;
                });

        device.setCurrentPasscode(passcode);
        device.setLockStatus(lockStatus);
        device.setLastEventTimestamp(LocalDateTime.now());

        return deviceRepository.save(device);
    }

    @Transactional(readOnly = true)
    public DeviceStatusResponse getDeviceStatus(String deviceId) {
        Device device = getDeviceByDeviceId(deviceId);

        return DeviceStatusResponse.builder()
                .data(DeviceStatusResponse.DeviceStatusData.builder()
                        .deviceId(device.getDeviceId())
                        .passcode(device.getCurrentPasscode())
                        .masterPasscode(device.getMasterPasscode())
                        .lockStatus(device.getLockStatus())
                        .lastEventTimestamp(device.getLastEventTimestamp() != null ?
                                device.getLastEventTimestamp().toEpochSecond(java.time.ZoneOffset.UTC) : null)
                        .build())
                .build();
    }

    @Transactional
    public void rotateMasterPasscode(String deviceId, String newMasterPasscode) {
        Device device = getDeviceByDeviceId(deviceId);
        device.setMasterPasscode(newMasterPasscode);
        device.setMasterPasscodeUpdatedAt(LocalDateTime.now());
        deviceRepository.save(device);
        log.info("Master passcode rotated for device: {}", deviceId);
    }
}