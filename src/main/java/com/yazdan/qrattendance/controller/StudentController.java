package com.yazdan.qrattendance.controller;

import com.yazdan.qrattendance.dto.SubmitScanRequest;
import com.yazdan.qrattendance.dto.SubmitScanResponse;
import com.yazdan.qrattendance.service.ScanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {
    private final ScanService scanService;

    @PostMapping("/scan")
    public SubmitScanResponse submitScan(@Valid @RequestBody SubmitScanRequest request) {
        return scanService.submitScan(request);
    }
}
