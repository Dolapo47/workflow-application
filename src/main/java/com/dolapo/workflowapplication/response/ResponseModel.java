package com.dolapo.workflowapplication.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseModel {
    private String status;
    private String message;
    private Object data;

}
