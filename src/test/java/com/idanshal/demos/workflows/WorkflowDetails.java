package com.idanshal.demos.workflows;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowDetails<T, R> {
    String workflowId;
    String workflowTaskQueue;
    List<Object> workflowArgs;
    Class<T> wfClass;
    Class<R> resultClass;
}
