package com.dolapo.workflowapplication.service;

import com.dolapo.workflowapplication.collection.WorkItem;
import com.dolapo.workflowapplication.repository.WorkItemRepository;
import com.dolapo.workflowapplication.response.ResponseModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WorkItemServiceTests {

    @Mock
    private WorkItemRepository workItemRepository;

    @InjectMocks
    private WorkItemService workItemService;

    String id = "";
    WorkItem workItem = new WorkItem();

    @BeforeEach
    public void init(){
        id = "work-item-"+System.currentTimeMillis();

        workItem.setId(id);
        workItem.setValue(2);
        workItem.setStatus("Processing");
        workItem.setCreatedAt(LocalDateTime.now().toString());
        workItem.setUpdatedAt(LocalDateTime.now().toString());
    }

    @Test
    public void WorkItemService_getWorkItems(){
        Page<WorkItem> workItems = Mockito.mock(Page.class);
        when(workItemRepository.findAll(Mockito.any(Pageable.class))).thenReturn(workItems);
        Page<WorkItem> workItemPage = workItemService.getWorkItems(1, 10);
        Assertions.assertThat(workItemPage).isNotNull();
    }

    @Test
    public void WorkItemService_getWorkItemById_return200(){
        when(workItemRepository.findById(id)).thenReturn(Optional.of(workItem));
        ResponseEntity<ResponseModel> workItemResponse = workItemService.getWorkItem(id);
        Assertions.assertThat(workItemResponse.getStatusCodeValue()).isEqualTo(200);
        Assertions.assertThat(Objects.requireNonNull(workItemResponse.getBody())
                .getMessage()).isEqualTo("work item retrieved successfully");
    }

    @Test
    public void WorkItemService_getWorkItemById_return404(){
        when(workItemRepository.findById(id)).thenReturn(Optional.empty());
        ResponseEntity<ResponseModel> workItemResponse = workItemService.getWorkItem(id);
        Assertions.assertThat(workItemResponse.getStatusCodeValue()).isEqualTo(404);
        Assertions.assertThat(Objects.requireNonNull(workItemResponse.getBody()).getMessage()).isEqualTo("Work item not found");
    }

    @Test
    public void WorkItemService_deleteWorkItemById_return200(){

        when(workItemRepository.findById(id)).thenReturn(Optional.ofNullable(workItem));

        ResponseEntity<ResponseModel> workItemResponse = workItemService.deleteWorkItem(id);
        Assertions.assertThat(workItemResponse.getStatusCodeValue()).isEqualTo(200);
        Assertions.assertThat(Objects.requireNonNull(workItemResponse.getBody())
                .getMessage()).isEqualTo("work item deleted successfully");
    }

    @Test
    public void WorkItemService_deleteWorkItemById_return404(){

        when(workItemRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<ResponseModel> workItemResponse = workItemService.deleteWorkItem(id);
        Assertions.assertThat(workItemResponse.getStatusCodeValue()).isEqualTo(404);
        Assertions.assertThat(Objects.requireNonNull(workItemResponse.getBody())
                .getMessage()).isEqualTo("Work item not found");
    }
}
