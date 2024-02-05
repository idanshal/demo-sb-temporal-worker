package com.idanshal.demos.activities;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface SubscriptionActivity {
    void onboardToFreeTrial(String customerIdentifier);
    void upgradeFromTrialToPaid(String customerIdentifier);
    void chargeMonthlyFee(String customerIdentifier);
    void processSubscriptionCancellation(String customerIdentifier);
}
