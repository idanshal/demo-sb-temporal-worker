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
    public <T, R> R executeAsyncWorkflowAndWait(WorkflowDetails<T, R> workflowDetails) {
        WorkflowStub workflowStub = startWorkflow(workflowDetails);
        return waitForResult(workflowStub, workflowDetails.getResultClass());
    }

    public <T, R> WorkflowStub startWorkflow(WorkflowDetails<T, R> workflowDetails) {
        WorkflowOptions workflowOptions = buildWorkflowOptions(workflowDetails);
        T wfStubClass = workflowClient.newWorkflowStub(workflowDetails.getWfClass(), workflowOptions);
        WorkflowStub workflowStub = WorkflowStub.fromTyped(wfStubClass);
        if (workflowDetails.getWorkflowArgs()!=null) {
            workflowStub.start(workflowDetails.getWorkflowArgs().toArray());
        } else {
            workflowStub.start();
        }
        return workflowStub;
    }

    public <R> R waitForResult(WorkflowStub workflowStub, Class<R> resultClass) {
        try {
            return workflowStub.getResult(resultClass);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private <T, R> WorkflowOptions buildWorkflowOptions(WorkflowDetails<T, R> workflowDetails) {
        return WorkflowOptions.newBuilder()
                .setTaskQueue(workflowDetails.getWorkflowTaskQueue())
                .setWorkflowId(workflowDetails.getWorkflowId() != null ? workflowDetails.getWorkflowId() : UUID.randomUUID().toString())
                .build();
    }
}
