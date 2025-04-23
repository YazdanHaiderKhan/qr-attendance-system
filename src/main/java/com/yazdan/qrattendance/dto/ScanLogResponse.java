package com.yazdan.qrattendance.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ScanLogResponse {
    private String studentName;
    private String enrollmentNumber;
    private LocalDateTime scannedAt;
    private Boolean locationVerified;
}
