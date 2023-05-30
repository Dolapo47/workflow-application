package com.dolapo.workflowapplication.repository;

import com.dolapo.workflowapplication.collection.WorkItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkItemRepository extends MongoRepository<WorkItem, String> {
    Page<WorkItem> findWorkItemByValue(int value, Pageable pageable);
    List<WorkItem> findWorkItemByValue(int value);
}
