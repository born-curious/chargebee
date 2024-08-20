package com.chargebee.creditmanagement.datastores.impl;

import com.chargebee.creditmanagement.models.data.ServicePricing;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ServicePricingDatastore extends ConcurrentHashMap<String, ServicePricing> {

    private static ServicePricingDatastore instance = null;

    public static ServicePricingDatastore getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (ServicePricingDatastore.class) {
                if (Objects.isNull(instance)) {
                    instance = new ServicePricingDatastore();
                }
            }
        }
        return instance;
    }

    private ServicePricingDatastore() {
        super();
    }
}
