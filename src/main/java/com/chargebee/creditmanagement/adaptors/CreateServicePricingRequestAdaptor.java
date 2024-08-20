package com.chargebee.creditmanagement.adaptors;

import com.chargebee.creditmanagement.models.data.ServicePricing;
import com.chargebee.creditmanagement.models.requests.CreateServicePricingRequest;

import java.util.Objects;
import java.util.function.Function;

public class CreateServicePricingRequestAdaptor implements Function<CreateServicePricingRequest, ServicePricing> {

    private static CreateServicePricingRequestAdaptor instance = null;

    @Override
    public ServicePricing apply(CreateServicePricingRequest createServicePricingRequest) {
        return ServicePricing.builder()
                .serviceId(createServicePricingRequest.getServiceName())
                .serviceName(createServicePricingRequest.getServiceName())
                .usageCost(createServicePricingRequest.getUsageCost())
                .build();
    }

    public static CreateServicePricingRequestAdaptor getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (CreateServicePricingRequestAdaptor.class) {
                if (Objects.isNull(instance)) {
                    instance = new CreateServicePricingRequestAdaptor();
                }
            }
        }
        return instance;
    }

    private CreateServicePricingRequestAdaptor() {
    }
}
