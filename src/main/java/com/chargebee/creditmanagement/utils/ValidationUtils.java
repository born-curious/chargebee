package com.chargebee.creditmanagement.utils;

import com.chargebee.creditmanagement.models.exceptions.ValidationException;
import com.chargebee.creditmanagement.models.requests.CreateServicePricingRequest;
import com.chargebee.creditmanagement.models.requests.CreditPackageRequest;
import com.chargebee.creditmanagement.models.requests.PurchaseCreditPackageRequest;
import com.chargebee.creditmanagement.models.requests.ServiceUsageRequest;
import com.chargebee.creditmanagement.models.requests.UpdateServicePricingRequest;

import java.math.BigDecimal;
import java.util.Objects;

import static com.chargebee.creditmanagement.constants.Constants.NumericConstants.ONE;
import static com.chargebee.creditmanagement.constants.Constants.NumericConstants.ZERO;

public final class ValidationUtils {

    public static void validateCreditPackageRequest(CreditPackageRequest creditPackageRequest) {
        if (Objects.isNull(creditPackageRequest.getCreditPackageType())) {
            throw new ValidationException("Credit Package can't be null");
        }
        if (creditPackageRequest.getCredits().compareTo(BigDecimal.valueOf(ONE)) < 0) {
            throw new ValidationException("No Of Credits in a package can't be less than 1");
        }
        if (creditPackageRequest.getCost().compareTo(BigDecimal.valueOf(ZERO)) < 0) {
            throw new ValidationException("Cost of a package can't be less than 0");
        }
    }

    public static void validateServicePricingRequest(CreateServicePricingRequest createServicePricingRequest) {
        validateServicePricingRequest(createServicePricingRequest.getServiceName(),
                createServicePricingRequest.getUsageCost());
    }

    public static void validateServicePricingRequest(UpdateServicePricingRequest updateServicePricingRequest) {
        validateServicePricingRequest(updateServicePricingRequest.getServiceId(),
                updateServicePricingRequest.getUsageCost());
    }

    public static void validatePurchaseCreditPackageRequest(PurchaseCreditPackageRequest purchaseCreditPackageRequest) {
        if (Objects.isNull(purchaseCreditPackageRequest.getUserId()) || purchaseCreditPackageRequest.getUserId()
                .isEmpty()) {
            throw new ValidationException("userId can't be null / empty");
        }
        if (Objects.isNull(purchaseCreditPackageRequest.getCreditPackageType())) {
            throw new ValidationException("Credit Package can't be null");
        }
    }

    public static void validateServiceUsageRequest(ServiceUsageRequest serviceUsageRequest) {
        if (Objects.isNull(serviceUsageRequest.getUserId()) || serviceUsageRequest.getUserId()
                .isEmpty()) {
            throw new ValidationException("userId can't be null / empty");
        }
    }

    private static void validateServicePricingRequest(String serviceName, BigDecimal usageCost) {
        if (Objects.isNull(serviceName) || serviceName.isEmpty()) {
            throw new ValidationException("Service Name can't be null / empty");
        }
        if (usageCost.compareTo(BigDecimal.valueOf(ZERO)) < 0) {
            throw new ValidationException("Usage Cost of a service can't be less than 0");
        }
    }

    private ValidationUtils() {
    }
}
