package com.chargebee.creditmanagement.daos.impl;

import com.chargebee.creditmanagement.daos.ServicePricingDao;
import com.chargebee.creditmanagement.datastores.impl.ServicePricingDatastore;
import com.chargebee.creditmanagement.models.data.ServicePricing;
import com.chargebee.creditmanagement.models.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServicePricingDaoImpl implements ServicePricingDao {

    private static ServicePricingDaoImpl instance = null;
    private final ServicePricingDatastore servicePricingDatastore;

    @Override
    public void insertServicePricing(ServicePricing servicePricing) {
        servicePricingDatastore.put(servicePricing.getServiceId(), servicePricing);
    }

    @Override
    public void updateServicePricing(ServicePricing servicePricing) {
        servicePricingDatastore.put(servicePricing.getServiceId(), servicePricing);
    }

    @Override
    public ServicePricing getServicePricing(String serviceId) {
        ServicePricing servicePricing = servicePricingDatastore.get(serviceId);
        if (Objects.isNull(servicePricing)) {
            throw new NotFoundException("Service Pricing Not Found for ServiceId - " + serviceId);
        }
        return servicePricing;
    }

    @Override
    public List<ServicePricing> list() {
        return new ArrayList<>(servicePricingDatastore.values());
    }

    public static ServicePricingDaoImpl getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (ServicePricingDaoImpl.class) {
                if (Objects.isNull(instance)) {
                    instance = new ServicePricingDaoImpl();
                }
            }
        }
        return instance;
    }

    private ServicePricingDaoImpl() {
        this.servicePricingDatastore = ServicePricingDatastore.getInstance();
    }
}
