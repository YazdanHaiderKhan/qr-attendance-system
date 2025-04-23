package com.yazdan.qrattendance.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.yazdan.qrattendance.dto.GenerateQrRequest;
import com.yazdan.qrattendance.dto.GenerateQrResponse;
import com.yazdan.qrattendance.entity.Classroom;
import com.yazdan.qrattendance.repository.ClassroomRepository;
import com.yazdan.qrattendance.service.QrService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QrServiceImpl implements QrService {
    private final ClassroomRepository classroomRepository;

    @Value("${attendance.qr-expiry-minutes:10}")
    private int qrExpiryMinutes;

    @Override
    @Transactional
    public GenerateQrResponse generateQr(GenerateQrRequest request) {
        Classroom classroom = classroomRepository.findById(request.getClassId())
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = now.plusMinutes(qrExpiryMinutes);
        // QR payload: classId|timestamp|lat|lng|expiresAt
        String payload = request.getClassId() + "|" + now + "|" + request.getLocationLat() + "|" + request.getLocationLng() + "|" + expiresAt;
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix matrix = new MultiFormatWriter().encode(payload, BarcodeFormat.QR_CODE, 300, 300, hints);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", baos);
            String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());
            classroom.setQrCodeData(base64);
            classroom.setLocationLat(request.getLocationLat());
            classroom.setLocationLng(request.getLocationLng());
            classroomRepository.save(classroom);
            GenerateQrResponse resp = new GenerateQrResponse();
            resp.setQrCodeBase64(base64);
            resp.setExpiresAt(expiresAt);
            return resp;
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate QR code", e);
        }
    }
}
