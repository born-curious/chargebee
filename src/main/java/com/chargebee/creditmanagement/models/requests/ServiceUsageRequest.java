package com.chargebee.creditmanagement.models.requests;

public class ServiceUsageRequest {

    private String serviceId;
    private String userId;

    public static ServiceUsageRequestBuilder builder() {
        return new ServiceUsageRequestBuilder();
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String toString() {
        String var10000 = this.getServiceId();
        return "ServiceUsageRequest(serviceId=" + var10000 + ", userId=" + this.getUserId() + ")";
    }

    public ServiceUsageRequest() {
    }

    public ServiceUsageRequest(String serviceId, String userId) {
        this.serviceId = serviceId;
        this.userId = userId;
    }

    public static class ServiceUsageRequestBuilder {

        private String serviceId;
        private String userId;

        ServiceUsageRequestBuilder() {
        }

        public ServiceUsageRequestBuilder serviceId(String serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        public ServiceUsageRequestBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public ServiceUsageRequest build() {
            return new ServiceUsageRequest(this.serviceId, this.userId);
        }

        public String toString() {
            return "ServiceUsageRequest.ServiceUsageRequestBuilder(serviceId=" + this.serviceId + ", userId="
                    + this.userId + ")";
        }
    }
}
