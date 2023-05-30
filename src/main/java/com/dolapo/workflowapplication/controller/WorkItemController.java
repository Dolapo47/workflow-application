package com.dolapo.workflowapplication.controller;

import com.dolapo.workflowapplication.dto.WorkItemDto;
import com.dolapo.workflowapplication.response.ResponseModel;
import com.dolapo.workflowapplication.service.WorkItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/workitem")
public class WorkItemController {

    private final WorkItemService workItemService;

    @PostMapping
    public ResponseEntity<ResponseModel> save(@RequestBody WorkItemDto workItem) {
        return workItemService.save(workItem);
    }

    @GetMapping("/{workItemId}")
    public ResponseEntity<ResponseModel> getWorkItem(@PathVariable String workItemId){
        return workItemService.getWorkItem(workItemId);
    }

    @DeleteMapping("/{workItemId}")
    public ResponseEntity<ResponseModel> deleteWorkItem(@PathVariable String workItemId){
        return workItemService.deleteWorkItem(workItemId);
    }
}
