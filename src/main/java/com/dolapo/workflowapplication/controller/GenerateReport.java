package com.dolapo.workflowapplication.controller;

import com.dolapo.workflowapplication.collection.WorkItem;
import com.dolapo.workflowapplication.repository.WorkItemRepository;
import com.dolapo.workflowapplication.response.ResponseModel;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
public class GenerateReport {

    private final WorkItemRepository workItemRepository;

    @GetMapping
    public ResponseEntity<ResponseModel> generateReport(){
        String filePath = "/Users/user/JaspersoftWorkspace/MyReports/workitem-report.jrxml";

        try{
            List<WorkItem> workItems = workItemRepository.findAll();
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("workItem", "Work Items");
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(workItems);
            JasperReport report = JasperCompileManager.compileReport(filePath);
            JasperPrint fillReport = JasperFillManager.fillReport(report, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(fillReport, "/Users/user/Downloads/firstReport.pdf");
            System.out.println("Report created...");
        }catch(Exception e){
            return null;
        }
        return null;
    }
}
