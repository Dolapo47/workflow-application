package com.dolapo.workflowapplication.repository;

import com.dolapo.workflowapplication.collection.WorkItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkItemRepository extends MongoRepository<WorkItem, String> {
}
