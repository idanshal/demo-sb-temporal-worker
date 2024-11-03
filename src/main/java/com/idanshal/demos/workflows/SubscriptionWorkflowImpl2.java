package com.idanshal.demos.workflows;

import com.idanshal.demos.common.workflows.SubscriptionWorkflow2;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;

import java.util.Map;


@NoArgsConstructor
@WorkflowImpl(taskQueues = "SubscriptionTaskQueue")
public class SubscriptionWorkflowImpl2 implements SubscriptionWorkflow2 {
    private final Logger logger = Workflow.getLogger(SubscriptionWorkflowImpl2.class);

    @Override
    public Map<String, String> execute(String customerIdentifier) {
        logger.info("Starting subscription workflow for customer: {}", customerIdentifier);
        return Map.of("customerId", customerIdentifier);
    }
}