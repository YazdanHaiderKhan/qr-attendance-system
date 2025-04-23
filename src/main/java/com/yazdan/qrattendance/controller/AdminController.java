package com.yazdan.qrattendance.controller;

import com.yazdan.qrattendance.dto.GenerateQrRequest;
import com.yazdan.qrattendance.dto.GenerateQrResponse;
import com.yazdan.qrattendance.dto.ScanLogResponse;
import com.yazdan.qrattendance.service.QrService;
import com.yazdan.qrattendance.repository.ScanLogRepository;
import com.yazdan.qrattendance.entity.ScanLog;
import com.yazdan.qrattendance.entity.Classroom;
import com.yazdan.qrattendance.util.HaversineUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final QrService qrService;
    private final ScanLogRepository scanLogRepository;

    @PostMapping("/generate-qr")
    public GenerateQrResponse generateQr(@Valid @RequestBody GenerateQrRequest request) {
        return qrService.generateQr(request);
    }

    @GetMapping("/scan-logs")
    public List<ScanLogResponse> getScanLogs(@RequestParam Long classId) {
        List<ScanLog> logs = scanLogRepository.findByClassroom_Id(classId);
        return logs.stream().map(log -> {
            ScanLogResponse resp = new ScanLogResponse();
            resp.setStudentName(log.getUser().getName());
            resp.setEnrollmentNumber(log.getUser().getEnrollmentNumber());
            resp.setScannedAt(log.getTimestamp());
            // locationVerified: within 50m (configurable)
            resp.setLocationVerified(HaversineUtil.distance(
                    log.getClassroom().getLocationLat(),
                    log.getClassroom().getLocationLng(),
                    log.getScannedLat(),
                    log.getScannedLng()) <= 50);
            return resp;
        }).collect(Collectors.toList());
    }
}
