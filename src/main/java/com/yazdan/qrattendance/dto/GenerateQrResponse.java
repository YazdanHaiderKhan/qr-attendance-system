package com.yazdan.qrattendance.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GenerateQrResponse {
    private String qrCodeBase64;
    private LocalDateTime expiresAt;
}
