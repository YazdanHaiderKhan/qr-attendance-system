package com.yazdan.qrattendance.service;

import com.yazdan.qrattendance.dto.GenerateQrRequest;
import com.yazdan.qrattendance.dto.GenerateQrResponse;

public interface QrService {
    GenerateQrResponse generateQr(GenerateQrRequest request);
}
