package com.chargebee.creditmanagement.utils;

import com.chargebee.creditmanagement.models.enums.CreditPackageType;
import com.chargebee.creditmanagement.models.exceptions.ValidationException;
import com.chargebee.creditmanagement.models.requests.CreateServicePricingRequest;
import com.chargebee.creditmanagement.models.requests.CreditPackageRequest;
import com.chargebee.creditmanagement.models.requests.PurchaseCreditPackageRequest;
import com.chargebee.creditmanagement.models.requests.ServiceUsageRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidationUtilsTest {

    @Test
    void testValidateCreditPackageRequest_NullCreditPackageType() {
        CreditPackageRequest creditPackageRequest = new CreditPackageRequest();
        creditPackageRequest.setCredits(BigDecimal.valueOf(2));
        creditPackageRequest.setCost(BigDecimal.valueOf(10));

        ValidationException exception = assertThrows(ValidationException.class,
                () -> ValidationUtils.validateCreditPackageRequest(creditPackageRequest));
        assertEquals("Credit Package can't be null", exception.getMessage());
    }

    @Test
    void testValidateCreditPackageRequest_CreditsLessThanOne() {
        CreditPackageRequest creditPackageRequest = new CreditPackageRequest();
        creditPackageRequest.setCreditPackageType(CreditPackageType.PREMIUM);
        creditPackageRequest.setCredits(BigDecimal.valueOf(0));
        creditPackageRequest.setCost(BigDecimal.valueOf(10));

        ValidationException exception = assertThrows(ValidationException.class,
                () -> ValidationUtils.validateCreditPackageRequest(creditPackageRequest));
        assertEquals("No Of Credits in a package can't be less than 1", exception.getMessage());
    }

    @Test
    void testValidateCreditPackageRequest_CostLessThanZero() {
        CreditPackageRequest creditPackageRequest = new CreditPackageRequest();
        creditPackageRequest.setCreditPackageType(CreditPackageType.PREMIUM);
        creditPackageRequest.setCredits(BigDecimal.valueOf(2));
        creditPackageRequest.setCost(BigDecimal.valueOf(-1));

        ValidationException exception = assertThrows(ValidationException.class,
                () -> ValidationUtils.validateCreditPackageRequest(creditPackageRequest));
        assertEquals("Cost of a package can't be less than 0", exception.getMessage());
    }

    @Test
    void testValidateServicePricingRequest_NullServiceName() {
        CreateServicePricingRequest createServicePricingRequest = new CreateServicePricingRequest();
        createServicePricingRequest.setUsageCost(BigDecimal.valueOf(10));

        ValidationException exception = assertThrows(ValidationException.class,
                () -> ValidationUtils.validateServicePricingRequest(createServicePricingRequest));
        assertEquals("Service Name can't be null / empty", exception.getMessage());
    }

    @Test
    void testValidateServicePricingRequest_UsageCostLessThanZero() {
        CreateServicePricingRequest createServicePricingRequest = new CreateServicePricingRequest();
        createServicePricingRequest.setServiceName("service");
        createServicePricingRequest.setUsageCost(BigDecimal.valueOf(-1));

        ValidationException exception = assertThrows(ValidationException.class,
                () -> ValidationUtils.validateServicePricingRequest(createServicePricingRequest));
        assertEquals("Usage Cost of a service can't be less than 0", exception.getMessage());
    }

    @Test
    void testValidatePurchaseCreditPackageRequest_NullUserId() {
        PurchaseCreditPackageRequest purchaseCreditPackageRequest = new PurchaseCreditPackageRequest();
        purchaseCreditPackageRequest.setCreditPackageType(CreditPackageType.PREMIUM);

        ValidationException exception = assertThrows(ValidationException.class,
                () -> ValidationUtils.validatePurchaseCreditPackageRequest(purchaseCreditPackageRequest));
        assertEquals("userId can't be null / empty", exception.getMessage());
    }

    @Test
    void testValidatePurchaseCreditPackageRequest_NullCreditPackageType() {
        PurchaseCreditPackageRequest purchaseCreditPackageRequest = new PurchaseCreditPackageRequest();
        purchaseCreditPackageRequest.setUserId("user");

        ValidationException exception = assertThrows(ValidationException.class,
                () -> ValidationUtils.validatePurchaseCreditPackageRequest(purchaseCreditPackageRequest));
        assertEquals("Credit Package can't be null", exception.getMessage());
    }

    @Test
    void testValidateServiceUsageRequest_NullUserId() {
        ServiceUsageRequest serviceUsageRequest = new ServiceUsageRequest();

        ValidationException exception = assertThrows(ValidationException.class,
                () -> ValidationUtils.validateServiceUsageRequest(serviceUsageRequest));
        assertEquals("userId can't be null / empty", exception.getMessage());
    }
}