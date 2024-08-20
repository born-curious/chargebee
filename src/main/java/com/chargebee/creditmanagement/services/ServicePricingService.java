package com.chargebee.creditmanagement.services;

import com.chargebee.creditmanagement.models.data.ServicePricing;
import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.requests.CreateServicePricingRequest;
import com.chargebee.creditmanagement.models.requests.ServiceUsageRequest;
import com.chargebee.creditmanagement.models.requests.UpdateServicePricingRequest;

import java.util.List;

public interface ServicePricingService {

    ServicePricing createServicePricing(CreateServicePricingRequest createServicePricingRequest);

    ServicePricing updateServicePricing(UpdateServicePricingRequest updateServicePricingRequest);

    ServicePricing getServicePricing(String serviceId);

    List<ServicePricing> viewAllServicePricing();

    Transaction utilizeService(ServiceUsageRequest serviceUsageRequest);
}
