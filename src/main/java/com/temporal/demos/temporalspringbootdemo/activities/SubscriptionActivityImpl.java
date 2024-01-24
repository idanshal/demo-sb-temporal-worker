package com.temporal.demos.temporalspringbootdemo.activities;

import io.temporal.spring.boot.ActivityImpl;
import io.temporal.workflow.Workflow;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ActivityImpl(taskQueues = "SubscriptionTaskQueue")
public class SubscriptionActivityImpl implements SubscriptionActivity {
    @Override
    @SneakyThrows
    public void onboardToFreeTrial(String customerIdentifier) {
        log.info("Onboarding customer to free trial: {}", customerIdentifier);
    }

    @Override
    public void upgradeFromTrialToPaid(String customerIdentifier) {
        log.info("Upgrading customer from trial to paid: {}", customerIdentifier);
    }

    @Override
    public void chargeMonthlyFee(String customerIdentifier) {
        log.info("Charging monthly fee to customer: {}", customerIdentifier);
    }

    @Override
    public void processSubscriptionCancellation(String customerIdentifier) {
        log.info("Processing subscription cancellation for customer: " + customerIdentifier);
    }
}
