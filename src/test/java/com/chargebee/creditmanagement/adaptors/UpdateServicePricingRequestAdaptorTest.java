package com.chargebee.creditmanagement.adaptors;

import com.chargebee.creditmanagement.models.data.ServicePricing;
import com.chargebee.creditmanagement.models.requests.UpdateServicePricingRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UpdateServicePricingRequestAdaptorTest {

    @Test
    public void testApply() {
        UpdateServicePricingRequest request = UpdateServicePricingRequest.builder()
                .serviceId("service123")
                .usageCost(BigDecimal.valueOf(10))
                .build();

        UpdateServicePricingRequestAdaptor adaptor = UpdateServicePricingRequestAdaptor.getInstance();

        ServicePricing servicePricing = adaptor.apply(request);

        assertNotNull(servicePricing);
        assertEquals("service123", servicePricing.getServiceId());
        assertEquals(BigDecimal.valueOf(10), servicePricing.getUsageCost());
    }
}
