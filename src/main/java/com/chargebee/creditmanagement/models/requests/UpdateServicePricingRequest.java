package com.chargebee.creditmanagement.models.requests;

import java.math.BigDecimal;

public class UpdateServicePricingRequest {

    private String serviceId;
    private BigDecimal usageCost;

    public static UpdateServicePricingRequestBuilder builder() {
        return new UpdateServicePricingRequestBuilder();
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public BigDecimal getUsageCost() {
        return this.usageCost;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setUsageCost(BigDecimal usageCost) {
        this.usageCost = usageCost;
    }

    public String toString() {
        String var10000 = this.getServiceId();
        return "UpdateServicePricingRequest(serviceId=" + var10000 + ", usageCost=" + this.getUsageCost() + ")";
    }

    public UpdateServicePricingRequest() {
    }

    public UpdateServicePricingRequest(String serviceId, BigDecimal usageCost) {
        this.serviceId = serviceId;
        this.usageCost = usageCost;
    }

    public static class UpdateServicePricingRequestBuilder {

        private String serviceId;
        private BigDecimal usageCost;

        UpdateServicePricingRequestBuilder() {
        }

        public UpdateServicePricingRequestBuilder serviceId(String serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        public UpdateServicePricingRequestBuilder usageCost(BigDecimal usageCost) {
            this.usageCost = usageCost;
            return this;
        }

        public UpdateServicePricingRequest build() {
            return new UpdateServicePricingRequest(this.serviceId, this.usageCost);
        }

        public String toString() {
            return "UpdateServicePricingRequest.UpdateServicePricingRequestBuilder(serviceId=" + this.serviceId
                    + ", usageCost=" + this.usageCost + ")";
        }
    }
}
