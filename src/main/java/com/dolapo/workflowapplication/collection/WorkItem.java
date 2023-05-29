package com.dolapo.workflowapplication.collection;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "work_item")
public class WorkItem {

    @Id
    private String id;
    private int value;
    private String status;
    private int result;

    @Field("created_at")
    private String createdAt;

    @Field("updated_at")
    private String updatedAt;
}
