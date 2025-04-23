package com.yazdan.qrattendance.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SubmitScanResponse {
    private String message;
    private LocalDateTime timestamp;
}
