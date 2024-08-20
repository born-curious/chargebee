package com.chargebee.creditmanagement.models.data;

import java.math.BigDecimal;

public class ServicePricing {

    private String serviceId;
    private String serviceName;
    private BigDecimal usageCost;

    public static ServicePricingBuilder builder() {
        return new ServicePricingBuilder();
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public BigDecimal getUsageCost() {
        return this.usageCost;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setUsageCost(BigDecimal usageCost) {
        this.usageCost = usageCost;
    }

    public String toString() {
        String var10000 = this.getServiceId();
        return "ServicePricing(serviceId=" + var10000 + ", serviceName=" + this.getServiceName() + ", usageCost="
                + this.getUsageCost() + ")";
    }

    public ServicePricing() {
    }

    public ServicePricing(String serviceId, String serviceName, BigDecimal usageCost) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.usageCost = usageCost;
    }

    public static class ServicePricingBuilder {

        private String serviceId;
        private String serviceName;
        private BigDecimal usageCost;

        ServicePricingBuilder() {
        }

        public ServicePricingBuilder serviceId(String serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        public ServicePricingBuilder serviceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public ServicePricingBuilder usageCost(BigDecimal usageCost) {
            this.usageCost = usageCost;
            return this;
        }

        public ServicePricing build() {
            return new ServicePricing(this.serviceId, this.serviceName, this.usageCost);
        }

        public String toString() {
            return "ServicePricing.ServicePricingBuilder(serviceId=" + this.serviceId + ", serviceName="
                    + this.serviceName + ", usageCost=" + this.usageCost + ")";
        }
    }
}
