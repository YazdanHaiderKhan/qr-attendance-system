package com.yazdan.qrattendance.service;

import com.yazdan.qrattendance.dto.SubmitScanRequest;
import com.yazdan.qrattendance.dto.SubmitScanResponse;

public interface ScanService {
    SubmitScanResponse submitScan(SubmitScanRequest request);
}
