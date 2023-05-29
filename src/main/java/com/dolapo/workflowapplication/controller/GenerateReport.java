package com.dolapo.workflowapplication.controller;

import com.dolapo.workflowapplication.collection.WorkItem;
import com.dolapo.workflowapplication.repository.WorkItemRepository;
import com.dolapo.workflowapplication.response.ResponseModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class GenerateReport {

    @Value("${jasper.report.file.path}")
    private String filePath;

    @Value("${jasper.report.dest.path}")
    private String destPath;

    private final WorkItemRepository workItemRepository;

    public GenerateReport(WorkItemRepository workItemRepository) {
        this.workItemRepository = workItemRepository;
    }

    @GetMapping
    public ResponseEntity<byte[]> generateReport(HttpServletResponse response){
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
            return null;
        }
        return new ResponseEntity<>(downloadedFile, headers, HttpStatus.OK);
    }
}
