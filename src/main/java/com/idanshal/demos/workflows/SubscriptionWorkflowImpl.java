package com.idanshal.demos.workflows;

import com.idanshal.demos.activities.SubscriptionActivity;
import com.idanshal.demos.common.workflows.SubscriptionWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.failure.CanceledFailure;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.CancellationScope;
import io.temporal.workflow.Workflow;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;

import java.time.Duration;


@NoArgsConstructor
@WorkflowImpl(taskQueues = "SubscriptionTaskQueue")
public class SubscriptionWorkflowImpl implements SubscriptionWorkflow {
    private final Logger logger = Workflow.getLogger(SubscriptionWorkflowImpl.class);
    private static final int TRAIL_SUBSCRIPTION_PERIOD = 7;
    private static final int PAID_SUBSCRIPTION_CHARGE_PERIOD = 7;
    private boolean upgradeApproved = false;
    private final SubscriptionActivity subscriptionActivity = Workflow.newActivityStub(SubscriptionActivity.class,
            ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(20)).build());

    private int paymentCount = 0;
    @Override
    public void execute(String customerIdentifier) {
        try {
            subscriptionActivity.onboardToFreeTrial(customerIdentifier);
            Workflow.sleep(Duration.ofSeconds(TRAIL_SUBSCRIPTION_PERIOD));
            logger.info("Trail subscription period is over! awaiting user approval for paid subscription. customerId: {}", customerIdentifier);
            Workflow.await(() -> upgradeApproved);
            subscriptionActivity.upgradeFromTrialToPaid(customerIdentifier);
            while (true) {
                Workflow.sleep(Duration.ofSeconds(PAID_SUBSCRIPTION_CHARGE_PERIOD));
                subscriptionActivity.chargeMonthlyFee(customerIdentifier);
                paymentCount++;
            }
        } catch (CanceledFailure e) {
            CancellationScope detached = Workflow.newDetachedCancellationScope(() ->
                    subscriptionActivity.processSubscriptionCancellation(customerIdentifier)
            );
            detached.run();
        }
    }

    @Override
    public void approveUpgrade() {
        upgradeApproved = true;
    }

    @Override
    public int getPaymentCount() {
        return paymentCount;
    }
}