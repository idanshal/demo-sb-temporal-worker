package com.idanshal.demos.workflows;

import com.idanshal.demos.common.workflows.SubscriptionWorkflow;
import com.idanshal.demos.common.workflows.SubscriptionWorkflow2;
import io.temporal.client.WorkflowClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubscriptionWorkflowImplTest {
    @Autowired
    WorkflowClient workflowClient;

    @Test
    void example1() {
        WorkflowHelper workflowHelper = new WorkflowHelper(workflowClient);
        WorkflowDetails<SubscriptionWorkflow, Void> workflowDetails = new WorkflowDetails<>();
        workflowDetails.setWorkflowId(UUID.randomUUID().toString());
        workflowDetails.setWorkflowTaskQueue("SubscriptionTaskQueue");
        workflowDetails.setWfClass(SubscriptionWorkflow.class);
        workflowDetails.setResultClass(Void.class);
        workflowHelper.startWorkflow(workflowDetails);
    }

    @Test
    void example2() {
        WorkflowHelper workflowHelper = new WorkflowHelper(workflowClient);
        WorkflowDetails<SubscriptionWorkflow2, Void> workflowDetails = new WorkflowDetails<>();
        workflowDetails.setWorkflowId(UUID.randomUUID().toString());
        workflowDetails.setWorkflowTaskQueue("SubscriptionTaskQueue");
        workflowDetails.setWfClass(SubscriptionWorkflow2.class);
        workflowDetails.setResultClass(Void.class);
        workflowDetails.setWorkflowArgs(List.of("123"));
        workflowHelper.executeAsyncWorkflowAndWait(workflowDetails);
    }


}