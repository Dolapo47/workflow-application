package com.dolapo.workflowapplication.service;

import com.dolapo.workflowapplication.collection.WorkItem;
import com.dolapo.workflowapplication.dto.WorkItemDto;
import com.dolapo.workflowapplication.producer.Producer;
import com.dolapo.workflowapplication.repository.WorkItemRepository;
import com.dolapo.workflowapplication.response.ResponseModel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WorkItemService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkItemService.class);
    private final WorkItemRepository workItemRepository;

    private final Producer producer;

    public ResponseEntity<ResponseModel> save(WorkItemDto workItemDto) {
        String id = "";
        WorkItem workItem = new WorkItem();
        if(workItemDto.getValue() < 1 || workItemDto.getValue() > 10){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                    new ResponseModel("422", "Value must be between the range of 1 and 10", null)
            );
        }
        try{
            id = "work-item-"+System.currentTimeMillis();
            workItem.setId(id);
            workItem.setValue(workItemDto.getValue());
            workItem.setStatus("Processing");
            workItem.setCreatedAt(LocalDateTime.now().toString());
            workItem.setUpdatedAt(LocalDateTime.now().toString());
            workItemRepository.save(workItem);
            producer.sendJsonMessage(workItem);
            Thread.sleep(workItem.getValue() * 10L);
        }catch(InterruptedException e){
            LOGGER.info("An interrupted Exception occurred", e);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                    new ResponseModel("422", "Exception occurred due to thread interruption", null)
            );
        } catch(Exception e){
            LOGGER.info("An interrupted Exception occurred", e);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                    new ResponseModel("422", e.getMessage(), null)
            );
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel("201", "work item created", null)
        );
    }

    public Page<WorkItem> getWorkItems(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return workItemRepository.findAll(pageable);
    }
    public Page<WorkItem> getWorkItemsByValue(int value, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return workItemRepository.findWorkItemByValue(value, pageable);
    }

    public ResponseEntity<ResponseModel> getWorkItem(String workItemId) {
        Optional<WorkItem> savedWorkItem;
        try{
            savedWorkItem = workItemRepository.findById(workItemId);
            if(savedWorkItem.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseModel("404", "Work item not found", null));
            }
        }catch(Exception e){
            LOGGER.info("An interrupted Exception occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseModel("500", "An error occurred", null)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel("200", "work item retrieved successfully", savedWorkItem.get()));
    }

    public ResponseEntity<ResponseModel> deleteWorkItem(String workItemId) {
        Optional<WorkItem> savedWorkItem;
        try{
            savedWorkItem = workItemRepository.findById(workItemId);
            if(savedWorkItem.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseModel("404", "Work item not found", null));
            }
            workItemRepository.delete(savedWorkItem.get());
        }catch(Exception e){
            LOGGER.info("An interrupted Exception occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseModel("500", "An error occurred", null)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseModel("200", "work item deleted successfully", null));
    }
}
