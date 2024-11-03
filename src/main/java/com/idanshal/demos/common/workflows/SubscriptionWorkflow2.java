package com.idanshal.demos.common.workflows;

import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

import java.util.Map;

@WorkflowInterface
public interface SubscriptionWorkflow2 {
    @WorkflowMethod
    Map<String,String> execute(String customerIdentifier);
}