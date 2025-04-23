package com.yazdan.qrattendance.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GenerateQrRequest {
    @NotNull
    private Long classId;
    @NotNull
    private Double locationLat;
    @NotNull
    private Double locationLng;
}
