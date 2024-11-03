package com.idanshal.demos.workflows;

import com.idanshal.demos.common.workflows.SubscriptionWorkflow;
import com.idanshal.demos.common.workflows.SubscriptionWorkflow2;
import io.temporal.client.WorkflowClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SubscriptionWorkflowImplTest {
    @Autowired
    WorkflowClient workflowClient;

    @Test
    void example() {
        WorkflowHelper workflowHelper = new WorkflowHelper(workflowClient);
        WorkflowDetails<SubscriptionWorkflow2, Void> workflowDetails = new WorkflowDetails<>();
        workflowDetails.setWorkflowId(UUID.randomUUID().toString());
        workflowDetails.setWorkflowTaskQueue("SubscriptionTaskQueue");
        workflowDetails.setWfClass(SubscriptionWorkflow2.class);
        workflowDetails.setResultClass(Void.class);
        workflowDetails.setWorkflowArgs(List.of("123"));
        workflowHelper.executeAsyncWorkflowAndWait(workflowDetails);
    }

//    @Test
//    void example2() {
//        WorkflowHelper workflowHelper = new WorkflowHelper(workflowClient);
//
//        ParameterizedTypeReference<Map<String, Integer>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
//        };
//        WorkflowDetails<SubscriptionWorkflow2, Map<String, Integer>> workflowDetails =
//                WorkflowDetails.<SubscriptionWorkflow2, Map<String,Integer>>builder()
//                        .workflowId(UUID.randomUUID().toString())
//                        .workflowArgs(List.of("123"))
//                        .workflowTaskQueue("TDPTaskQueue_1")
//                        .wfClass(SubscriptionWorkflow2.class)
//                        .resultClass((Class<Map<String, Integer>>)((ParameterizedType) parameterizedTypeReference.getType()).getRawType())
//                        .build();
//
//        workflowHelper.executeAsyncWorkflowAndWait(workflowDetails);
//    }


}