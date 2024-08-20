package com.chargebee.creditmanagement.controllers;

import com.chargebee.creditmanagement.models.data.CreditPackage;
import com.chargebee.creditmanagement.models.data.ServicePricing;
import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.data.UserCredit;
import com.chargebee.creditmanagement.models.enums.CreditPackageType;
import com.chargebee.creditmanagement.models.requests.CreateServicePricingRequest;
import com.chargebee.creditmanagement.models.requests.CreditPackageRequest;
import com.chargebee.creditmanagement.models.requests.PurchaseCreditPackageRequest;
import com.chargebee.creditmanagement.models.requests.ServiceUsageRequest;
import com.chargebee.creditmanagement.models.requests.UpdateServicePricingRequest;
import com.chargebee.creditmanagement.services.CreditPackageService;
import com.chargebee.creditmanagement.services.ServicePricingService;
import com.chargebee.creditmanagement.services.TransactionService;
import com.chargebee.creditmanagement.services.UserCreditService;
import com.chargebee.creditmanagement.services.impl.CreditPackageServiceImpl;
import com.chargebee.creditmanagement.services.impl.ServicePricingServiceImpl;
import com.chargebee.creditmanagement.services.impl.TransactionServiceImpl;
import com.chargebee.creditmanagement.services.impl.UserCreditServiceImpl;

import java.util.List;
import java.util.Objects;

public class Controller {

    private static Controller instance = null;
    private final CreditPackageService creditPackageService;
    private final ServicePricingService servicePricingService;
    private final TransactionService transactionService;
    private final UserCreditService userCreditService;

    public CreditPackage createCreditPackage(CreditPackageRequest creditPackageRequest) {
        return creditPackageService.createCreditPackage(creditPackageRequest);
    }

    public CreditPackage updateCreditPackage(CreditPackageRequest creditPackageRequest) {
        return creditPackageService.updateCreditPackage(creditPackageRequest);
    }

    public CreditPackage getCreditPackage(CreditPackageType creditPackageType) {
        return creditPackageService.getCreditPackage(creditPackageType);
    }

    public List<CreditPackage> viewAllPackages() {
        return creditPackageService.viewAllPackages();
    }

    public Transaction purchaseCredits(PurchaseCreditPackageRequest purchaseCreditPackageRequest) {
        return creditPackageService.purchaseCredits(purchaseCreditPackageRequest);
    }

    public ServicePricing createServicePricing(CreateServicePricingRequest createServicePricingRequest) {
        return servicePricingService.createServicePricing(createServicePricingRequest);
    }

    public ServicePricing updateServicePricing(UpdateServicePricingRequest updateServicePricingRequest) {
        return servicePricingService.updateServicePricing(updateServicePricingRequest);
    }

    public ServicePricing getServicePricing(String serviceId) {
        return servicePricingService.getServicePricing(serviceId);
    }

    public List<ServicePricing> viewAllServicePricing() {
        return servicePricingService.viewAllServicePricing();
    }

    public Transaction utilizeService(ServiceUsageRequest serviceUsageRequest) {
        return servicePricingService.utilizeService(serviceUsageRequest);
    }

    public Transaction getTransaction(String transactionId) {
        return transactionService.getTransaction(transactionId);
    }

    public List<Transaction> viewAllTransactions(String userId) {
        return transactionService.viewAllTransactions(userId);
    }

    public UserCredit getUserCredit(String userId) {
        return userCreditService.getUserCredit(userId);
    }

    public static Controller getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (Controller.class) {
                if (Objects.isNull(instance)) {
                    instance = new Controller();
                }
            }
        }
        return instance;
    }

    private Controller() {
        this.creditPackageService = CreditPackageServiceImpl.getInstance();
        this.servicePricingService = ServicePricingServiceImpl.getInstance();
        this.transactionService = TransactionServiceImpl.getInstance();
        this.userCreditService = UserCreditServiceImpl.getInstance();
    }
}