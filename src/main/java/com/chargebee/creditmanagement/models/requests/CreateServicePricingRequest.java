package com.chargebee.creditmanagement.models.requests;

import java.math.BigDecimal;

public class CreateServicePricingRequest {

    private String serviceName;
    private BigDecimal usageCost;

    public static CreateServicePricingRequestBuilder builder() {
        return new CreateServicePricingRequestBuilder();
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public BigDecimal getUsageCost() {
        return this.usageCost;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setUsageCost(BigDecimal usageCost) {
        this.usageCost = usageCost;
    }

    public String toString() {
        String var10000 = this.getServiceName();
        return "CreateServicePricingRequest(serviceName=" + var10000 + ", usageCost=" + this.getUsageCost() + ")";
    }

    public CreateServicePricingRequest() {
    }

    public CreateServicePricingRequest(String serviceName, BigDecimal usageCost) {
        this.serviceName = serviceName;
        this.usageCost = usageCost;
    }

    public static class CreateServicePricingRequestBuilder {

        private String serviceName;
        private BigDecimal usageCost;

        CreateServicePricingRequestBuilder() {
        }

        public CreateServicePricingRequestBuilder serviceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public CreateServicePricingRequestBuilder usageCost(BigDecimal usageCost) {
            this.usageCost = usageCost;
            return this;
        }

        public CreateServicePricingRequest build() {
            return new CreateServicePricingRequest(this.serviceName, this.usageCost);
        }

        public String toString() {
            return "CreateServicePricingRequest.CreateServicePricingRequestBuilder(serviceName=" + this.serviceName
                    + ", usageCost=" + this.usageCost + ")";
        }
    }
}
