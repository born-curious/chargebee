package com.chargebee.creditmanagement;

import com.chargebee.creditmanagement.controllers.Controller;
import com.chargebee.creditmanagement.models.data.ServicePricing;
import com.chargebee.creditmanagement.models.data.UserCredit;
import com.chargebee.creditmanagement.models.enums.CreditPackageType;
import com.chargebee.creditmanagement.models.requests.CreateServicePricingRequest;
import com.chargebee.creditmanagement.models.requests.CreditPackageRequest;
import com.chargebee.creditmanagement.models.requests.PurchaseCreditPackageRequest;
import com.chargebee.creditmanagement.models.requests.ServiceUsageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedScript {

    public static void main(String[] args) throws InterruptedException {
        Controller controller = Controller.getInstance();

        CreditPackageRequest basicRequest = new CreditPackageRequest(CreditPackageType.BASIC, BigDecimal.valueOf(10),
                BigDecimal.valueOf(100));
        CreditPackageRequest standardRequest = new CreditPackageRequest(CreditPackageType.STANDARD,
                BigDecimal.valueOf(20), BigDecimal.valueOf(200));
        CreditPackageRequest premiumRequest = new CreditPackageRequest(CreditPackageType.PREMIUM,
                BigDecimal.valueOf(30), BigDecimal.valueOf(300));

        controller.createCreditPackage(basicRequest);
        controller.createCreditPackage(standardRequest);
        controller.createCreditPackage(premiumRequest);

        CreateServicePricingRequest service1Request = new CreateServicePricingRequest("Service 1",
                BigDecimal.valueOf(5));
        CreateServicePricingRequest service2Request = new CreateServicePricingRequest("Service 2",
                BigDecimal.valueOf(10));
        CreateServicePricingRequest service3Request = new CreateServicePricingRequest("Service 3",
                BigDecimal.valueOf(15));

        ServicePricing service1 = controller.createServicePricing(service1Request);
        ServicePricing service2 = controller.createServicePricing(service2Request);
        ServicePricing service3 = controller.createServicePricing(service3Request);

        List<ServicePricing> servicePricingList = new ArrayList<>(Arrays.asList(service1, service2, service3));

        Random random = new Random();

        String userId = "user1";
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 20; i++) {
            if (i % 10 == 1) {
                PurchaseCreditPackageRequest purchaseRequest = new PurchaseCreditPackageRequest(CreditPackageType.BASIC,
                        userId);
                int finalI = i;
                executor.submit(() -> {
                    try {
                        System.out.println(finalI + " " + controller.purchaseCredits(purchaseRequest));
                    } catch (Exception e) {
                        System.out.println(finalI + " " + e.getMessage());
                    }
                });
            } else {
                int index = random.nextInt(3);
                int finalI = i;
                ServiceUsageRequest usageRequest = new ServiceUsageRequest(servicePricingList.get(index).getServiceId(),
                        userId);
                executor.submit(() -> {
                    try {
                        System.out.println(finalI + " " + controller.utilizeService(usageRequest));
                    } catch (Exception e) {
                        System.out.println(finalI + " " + e.getMessage());
                    }
                });
            }
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
            Thread.sleep(10);
        }

        System.out.println(controller.viewAllTransactions(userId));

        UserCredit userCredit = controller.getUserCredit(userId);
        System.out.println("User credits: " + userCredit);
    }
}
