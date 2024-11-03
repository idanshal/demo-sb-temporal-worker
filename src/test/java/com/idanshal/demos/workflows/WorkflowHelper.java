package com.idanshal.demos.workflows;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class WorkflowHelper {

    private final WorkflowClient workflowClient;

    @SneakyThrows
    public <T, R> void executeAsyncWorkflowAndWait(WorkflowDetails<T, R> workflowDetails) {
        WorkflowStub workflowStub = startWorkflow(workflowDetails);
        Class<?> resultClass = workflowDetails.getResultClass() != null ? workflowDetails.getResultClass() : Void.class;
        waitForResult(workflowStub, resultClass);
    }

    public <T, R> WorkflowStub startWorkflow(WorkflowDetails<T, R> workflowDetails) {
        WorkflowOptions workflowOptions = buildWorkflowOptions(workflowDetails);
        T wfStubClass = workflowClient.newWorkflowStub(workflowDetails.getWfClass(), workflowOptions);
        WorkflowStub workflowStub = WorkflowStub.fromTyped(wfStubClass);
        workflowStub.start(workflowDetails.getWorkflowArgs().toArray());
        return workflowStub;
    }

    public <R> void waitForResult(WorkflowStub workflowStub, Class<R> resultClass) {
        try {
            workflowStub.getResult(resultClass);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private <T, R> WorkflowOptions buildWorkflowOptions(WorkflowDetails<T, R> workflowDetails) {
        return WorkflowOptions.newBuilder()
                .setTaskQueue(workflowDetails.getWorkflowTaskQueue())
                .setWorkflowId(workflowDetails.getWorkflowId() != null ? workflowDetails.getWorkflowId() : UUID.randomUUID().toString())
                .build();
    }
}
