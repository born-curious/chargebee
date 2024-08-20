package com.chargebee.creditmanagement.daos;

import com.chargebee.creditmanagement.models.data.ServicePricing;

import java.util.List;

public interface ServicePricingDao {

    void insertServicePricing(ServicePricing servicePricing);

    void updateServicePricing(ServicePricing servicePricing);

    ServicePricing getServicePricing(String serviceId);

    List<ServicePricing> list();

}
