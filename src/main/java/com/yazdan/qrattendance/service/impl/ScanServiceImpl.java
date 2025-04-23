package com.yazdan.qrattendance.service.impl;

import com.yazdan.qrattendance.dto.SubmitScanRequest;
import com.yazdan.qrattendance.dto.SubmitScanResponse;
import com.yazdan.qrattendance.entity.Classroom;
import com.yazdan.qrattendance.entity.ScanLog;
import com.yazdan.qrattendance.entity.User;
import com.yazdan.qrattendance.repository.ClassroomRepository;
import com.yazdan.qrattendance.repository.ScanLogRepository;
import com.yazdan.qrattendance.repository.UserRepository;
import com.yazdan.qrattendance.service.ScanService;
import com.yazdan.qrattendance.util.HaversineUtil;
import com.yazdan.qrattendance.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ScanServiceImpl implements ScanService {
    private final ScanLogRepository scanLogRepository;
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    @Value("${attendance.max-distance-meters:50}")
    private double maxDistanceMeters;
    @Value("${attendance.qr-expiry-minutes:10}")
    private int qrExpiryMinutes;

    @Override
    @Transactional
    public SubmitScanResponse submitScan(SubmitScanRequest request) {
        User user = securityUtil.getCurrentUser();
        Classroom classroom = classroomRepository.findById(request.getClassId())
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
        // QR expiry validation
        LocalDateTime now = LocalDateTime.now();
        if (classroom.getQrExpiresAt() == null || classroom.getQrExpiresAt().isBefore(now)) {
            throw new IllegalArgumentException("QR code expired");
        }
        // Distance validation
        double distance = HaversineUtil.distance(
                classroom.getLocationLat(), classroom.getLocationLng(),
                request.getScannedLat(), request.getScannedLng()
        );
        boolean locationVerified = distance <= maxDistanceMeters;
        // Already scanned today?
        LocalDate today = now.toLocalDate();
        scanLogRepository.findByUserAndClassroomAndScanDate(user, classroom, today)
                .ifPresent(log -> { throw new IllegalArgumentException("Already scanned today"); });
        // Save log
        ScanLog log = ScanLog.builder()
                .user(user)
                .classroom(classroom)
                .scannedLat(request.getScannedLat())
                .scannedLng(request.getScannedLng())
                .scanDate(today)
                .build();
        scanLogRepository.save(log);
        SubmitScanResponse resp = new SubmitScanResponse();
        resp.setMessage(locationVerified ? "Attendance marked" : "Attendance marked (location not verified)");
        resp.setTimestamp(now);
        return resp;
    }
}
