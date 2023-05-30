package com.dolapo.workflowapplication.service;

import com.dolapo.workflowapplication.collection.WorkItem;
import com.dolapo.workflowapplication.repository.WorkItemRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenerateReportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateReportService.class);
    private final WorkItemRepository workItemRepository;

    @Value("${jasper.report.file.path}")
    private String filePath;

    public GenerateReportService(WorkItemRepository workItemRepository) {
        this.workItemRepository = workItemRepository;
    }

    public ResponseEntity<byte[]> generateReport(){
        byte[] downloadedFile;
        HttpHeaders headers = new HttpHeaders();
        try{
            List<WorkItem> workItems = workItemRepository.findAll();
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("workItem", "Work Items");
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(workItems);
            JasperReport report = JasperCompileManager.compileReport(filePath);
            JasperPrint fillReport = JasperFillManager.fillReport(report, parameters, dataSource);
            downloadedFile = JasperExportManager.exportReportToPdf(fillReport);
            headers.setContentType(MediaType.valueOf("application/pdf"));
            ContentDisposition build = ContentDisposition
                    .builder("attachment")
                    .filename("WorkItems")
                    .build();
            headers.setContentDisposition(build);
        }catch(Exception e){
            LOGGER.error("An error occurred while generating report", e);
            return null;
        }
        return new ResponseEntity<>(downloadedFile, headers, HttpStatus.OK);
    }
}
