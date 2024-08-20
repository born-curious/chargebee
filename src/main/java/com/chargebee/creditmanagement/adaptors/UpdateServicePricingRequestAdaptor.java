package com.chargebee.creditmanagement.adaptors;

import com.chargebee.creditmanagement.models.data.ServicePricing;
import com.chargebee.creditmanagement.models.requests.UpdateServicePricingRequest;

import java.util.Objects;
import java.util.function.Function;

public class UpdateServicePricingRequestAdaptor implements Function<UpdateServicePricingRequest, ServicePricing> {

    private static UpdateServicePricingRequestAdaptor instance = null;

    @Override
    public ServicePricing apply(UpdateServicePricingRequest updateServicePricingRequest) {
        return ServicePricing.builder()
                .serviceId(updateServicePricingRequest.getServiceId())
                .usageCost(updateServicePricingRequest.getUsageCost())
                .build();
    }

    public static UpdateServicePricingRequestAdaptor getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (UpdateServicePricingRequestAdaptor.class) {
                if (Objects.isNull(instance)) {
                    instance = new UpdateServicePricingRequestAdaptor();
                }
            }
        }
        return instance;
    }

    private UpdateServicePricingRequestAdaptor() {
    }
}
