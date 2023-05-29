package com.dolapo.workflowapplication;

import com.dolapo.workflowapplication.collection.WorkItem;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class WorkFlowApplication {
    public static void main(String[] args) {
//        try{
//            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("workItem", "work-item-1");
//            WorkItem workItem = new WorkItem();
//            workItem.setId("work-item-1");
//            workItem.setValue(2);
//            workItem.setStatus("processed");
//            workItem.setCreatedAt("25-nov-2015");
//            workItem.setUpdatedAt("25-nov-2015");
//            List<WorkItem> workItems = new ArrayList<>();
//            workItems.add(workItem);
//            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(workItems);
//            JasperReport report = JasperCompileManager.compileReport(filePath);
//            JasperPrint fillReport = JasperFillManager.fillReport(report, parameters, dataSource);
//            JasperExportManager.exportReportToPdfFile(fillReport, "/Users/user/Downloads/firstReport.pdf");
//            System.out.println("Report created...");
//        }catch(Exception e){
//
//        }
        SpringApplication.run(WorkFlowApplication.class, args);
    }

}
