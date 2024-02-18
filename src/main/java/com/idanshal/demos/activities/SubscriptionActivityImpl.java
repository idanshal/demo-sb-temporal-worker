package com.idanshal.demos.activities;

import com.idanshal.demos.common.entities.Subscription;
import com.idanshal.demos.common.enums.SubscriptionType;
import com.idanshal.demos.common.repository.SubscriptionRepository;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ActivityImpl(taskQueues = "SubscriptionTaskQueue")
@RequiredArgsConstructor
public class SubscriptionActivityImpl implements SubscriptionActivity {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    @SneakyThrows
    public void onboardToFreeTrial(String customerIdentifier) {
        log.info("Onboarding customer to free trial: {}", customerIdentifier);
        Subscription subscription = getSubscriptionByCustomerId(customerIdentifier);
        subscription.setSubscriptionType(SubscriptionType.TRIAL);
        subscription.setActive(true);
        subscriptionRepository.save(subscription);
    }

    @Override
    public void upgradeFromTrialToPaid(String customerIdentifier) {
        log.info("Upgrading customer from trial to paid: {}", customerIdentifier);
        Subscription subscription = getSubscriptionByCustomerId(customerIdentifier);
        subscription.setSubscriptionType(SubscriptionType.PAID);
        subscriptionRepository.save(subscription);
    }

    @Override
    public void chargeMonthlyFee(String customerIdentifier) {
        log.info("Charging monthly fee to customer: {}", customerIdentifier);
    }

    @Override
    public void processSubscriptionCancellation(String customerIdentifier) {
        log.info("Processing subscription cancellation for customer: " + customerIdentifier);
        Subscription subscription = getSubscriptionByCustomerId(customerIdentifier);
        subscription.setActive(false);
        subscriptionRepository.save(subscription);
    }

    private Subscription getSubscriptionByCustomerId(String customerIdentifier) {
        return subscriptionRepository.findById(customerIdentifier).orElseThrow(() -> new IllegalArgumentException("Subscription not found"));
    }
}
