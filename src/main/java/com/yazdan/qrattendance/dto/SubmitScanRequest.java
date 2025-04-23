package com.yazdan.qrattendance.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubmitScanRequest {
    @NotNull
    private Long classId;
    @NotNull
    private Double scannedLat;
    @NotNull
    private Double scannedLng;
}
