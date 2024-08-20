package com.chargebee.creditmanagement;

import com.chargebee.creditmanagement.controllers.Controller;
import com.chargebee.creditmanagement.models.data.CreditPackage;
import com.chargebee.creditmanagement.models.data.ServicePricing;
import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.enums.CreditPackageType;
import com.chargebee.creditmanagement.models.requests.CreateServicePricingRequest;
import com.chargebee.creditmanagement.models.requests.CreditPackageRequest;
import com.chargebee.creditmanagement.models.requests.PurchaseCreditPackageRequest;
import com.chargebee.creditmanagement.models.requests.ServiceUsageRequest;
import com.chargebee.creditmanagement.models.requests.UpdateServicePricingRequest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException {
        Controller controller = Controller.getInstance();
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] params = line.split(",");
                    cleanParams(params);
                    String methodName = params[0];
                    switch (methodName) {
                        case "createCreditPackage":
                            createCreditPackage(controller, params);
                            break;
                        case "updateCreditPackage":
                            updateCreditPackage(controller, params);
                            break;
                        case "getCreditPackage":
                            getCreditPackage(controller, params);
                            break;
                        case "viewAllPackages":
                            viewAllPackages(controller);
                            break;
                        case "purchaseCredits":
                            purchaseCredits(controller, params);
                            break;
                        case "createServicePricing":
                            createServicePricing(controller, params);
                            break;
                        case "updateServicePricing":
                            updateServicePricing(controller, params);
                            break;
                        case "getServicePricing":
                            getServicePricing(controller, params);
                            break;
                        case "viewAllServicePricing":
                            viewAllServicePricing(controller);
                            break;
                        case "utilizeService":
                            utilizeService(controller, params);
                            break;
                        case "getTransaction":
                            getTransaction(controller, params);
                            break;
                        case "viewAllTransactions":
                            viewAllTransactions(controller, params);
                            break;
                        case "getUserCredit":
                            getUserCredit(controller, params);
                            break;
                        default:
                            System.out.println("Invalid method name");
                    }
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private static void cleanParams(String[] params) {
        for (int i = 0; i < params.length; i++) {
            params[i] = params[i].replaceAll("\\s+", "");
        }
    }

    private static void createCreditPackage(Controller controller, String[] params) {
        CreditPackageRequest creditPackageRequest = CreditPackageRequest.builder()
                .creditPackageType(CreditPackageType.valueOf(params[1]))
                .cost(new BigDecimal(params[2]))
                .credits(new BigDecimal(params[3]))
                .build();
        controller.createCreditPackage(creditPackageRequest);
    }

    private static void updateCreditPackage(Controller controller, String[] params) {
        CreditPackageRequest creditPackageRequest = CreditPackageRequest.builder()
                .creditPackageType(CreditPackageType.valueOf(params[1]))
                .cost(new BigDecimal(params[2]))
                .credits(new BigDecimal(params[3]))
                .build();
        controller.updateCreditPackage(creditPackageRequest);
    }

    private static void getCreditPackage(Controller controller, String[] params) {
        CreditPackage creditPackage = controller.getCreditPackage(CreditPackageType.valueOf(params[1]));
        System.out.println(creditPackage);
    }

    private static void viewAllPackages(Controller controller) {
        System.out.println(controller.viewAllPackages());
    }

    private static void purchaseCredits(Controller controller, String[] params) {
        PurchaseCreditPackageRequest purchaseCreditPackageRequest = PurchaseCreditPackageRequest.builder()
                .creditPackageType(CreditPackageType.valueOf(params[1]))
                .userId(params[2])
                .build();
        controller.purchaseCredits(purchaseCreditPackageRequest);
    }

    private static void createServicePricing(Controller controller, String[] params) {
        CreateServicePricingRequest createServicePricingRequest = CreateServicePricingRequest.builder()
                .serviceName(params[1])
                .usageCost(new BigDecimal(params[2]))
                .build();
        controller.createServicePricing(createServicePricingRequest);
    }

    private static void updateServicePricing(Controller controller, String[] params) {
        UpdateServicePricingRequest updateServicePricingRequest = UpdateServicePricingRequest.builder()
                .serviceId(params[1])
                .usageCost(new BigDecimal(params[2]))
                .build();
        controller.updateServicePricing(updateServicePricingRequest);
    }

    private static void getServicePricing(Controller controller, String[] params) {
        ServicePricing servicePricing = controller.getServicePricing(params[1]);
        System.out.println(servicePricing);
    }

    private static void viewAllServicePricing(Controller controller) {
        System.out.println(controller.viewAllServicePricing());
    }

    private static void utilizeService(Controller controller, String[] params) {
        ServiceUsageRequest serviceUsageRequest = ServiceUsageRequest.builder()
                .serviceId(params[1])
                .userId(params[2])
                .build();
        controller.utilizeService(serviceUsageRequest);
    }

    private static void getTransaction(Controller controller, String[] params) {
        Transaction transaction = controller.getTransaction(params[1]);
        System.out.println(transaction);
    }

    private static void viewAllTransactions(Controller controller, String[] params) {
        System.out.println(controller.viewAllTransactions(params[1]));
    }

    private static void getUserCredit(Controller controller, String[] params) {
        System.out.println(controller.getUserCredit(params[1]));
    }
}