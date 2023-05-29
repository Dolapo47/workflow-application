package com.dolapo.workflowapplication.controller;

import com.dolapo.workflowapplication.service.GenerateReportService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class GenerateReportController {

    private final GenerateReportService generateReportService;

    public GenerateReportController(GenerateReportService generateReportService) {
        this.generateReportService = generateReportService;
    }

    @GetMapping
    public ResponseEntity<byte[]> generateReport(){
        return generateReportService.generateReport();
    }
}
