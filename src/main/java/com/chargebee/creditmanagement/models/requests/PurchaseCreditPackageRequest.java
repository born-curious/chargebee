package com.chargebee.creditmanagement.models.requests;

import com.chargebee.creditmanagement.models.enums.CreditPackageType;

public class PurchaseCreditPackageRequest {

    private CreditPackageType creditPackageType;
    private String userId;

    public static PurchaseCreditPackageRequestBuilder builder() {
        return new PurchaseCreditPackageRequestBuilder();
    }

    public CreditPackageType getCreditPackageType() {
        return this.creditPackageType;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setCreditPackageType(CreditPackageType creditPackageType) {
        this.creditPackageType = creditPackageType;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String toString() {
        CreditPackageType var10000 = this.getCreditPackageType();
        return "PurchaseCreditPackageRequest(creditPackageType=" + var10000 + ", userId=" + this.getUserId() + ")";
    }

    public PurchaseCreditPackageRequest() {
    }

    public PurchaseCreditPackageRequest(CreditPackageType creditPackageType, String userId) {
        this.creditPackageType = creditPackageType;
        this.userId = userId;
    }

    public static class PurchaseCreditPackageRequestBuilder {

        private CreditPackageType creditPackageType;
        private String userId;

        PurchaseCreditPackageRequestBuilder() {
        }

        public PurchaseCreditPackageRequestBuilder creditPackageType(CreditPackageType creditPackageType) {
            this.creditPackageType = creditPackageType;
            return this;
        }

        public PurchaseCreditPackageRequestBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public PurchaseCreditPackageRequest build() {
            return new PurchaseCreditPackageRequest(this.creditPackageType, this.userId);
        }

        public String toString() {
            return "PurchaseCreditPackageRequest.PurchaseCreditPackageRequestBuilder(creditPackageType="
                    + this.creditPackageType + ", userId=" + this.userId + ")";
        }
    }
}
