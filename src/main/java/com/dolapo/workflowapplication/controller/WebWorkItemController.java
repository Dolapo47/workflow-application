package com.dolapo.workflowapplication.controller;

import com.dolapo.workflowapplication.collection.WorkItem;
import com.dolapo.workflowapplication.service.WorkItemService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebWorkItemController {

    private final WorkItemService workItemService;

    public WebWorkItemController(WorkItemService workItemService) {
        this.workItemService = workItemService;
    }

    @GetMapping("work-items/get/{value}")
    public String displayWorkItems(@PathVariable int value, @RequestParam(defaultValue = "1") int page, Model model){
        int pageSize = 10;
        if(page == 0){
            page = 1;
        }
        --page;


        Page<WorkItem> workItems = workItemService.getWorkItemsByValue(value, page, pageSize);
        model.addAttribute("workItems", workItems);
        model.addAttribute("page", ++page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", workItems.getTotalPages());
        model.addAttribute("totalItems", workItems.getTotalElements());
        model.addAttribute("value", value);
        return "list-workitems";
    }
}
