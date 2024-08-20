package com.chargebee.creditmanagement.adaptors;

import com.chargebee.creditmanagement.models.data.ServicePricing;
import com.chargebee.creditmanagement.models.requests.CreateServicePricingRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class CreateServicePricingRequestAdaptorTest {

    @Test
    public void testApply() {
        CreateServicePricingRequest request = CreateServicePricingRequest.builder()
                .serviceName("Test Service")
                .usageCost(BigDecimal.valueOf(10.0))
                .build();

        CreateServicePricingRequestAdaptor adaptor = CreateServicePricingRequestAdaptor.getInstance();

        ServicePricing servicePricing = adaptor.apply(request);

        Assertions.assertNotNull(servicePricing);
        Assertions.assertEquals("Test Service", servicePricing.getServiceId());
        Assertions.assertEquals("Test Service", servicePricing.getServiceName());
        Assertions.assertEquals(BigDecimal.valueOf(10.0), servicePricing.getUsageCost());
    }
}


