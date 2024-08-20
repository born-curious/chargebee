package com.chargebee.creditmanagement.models.requests;

import com.chargebee.creditmanagement.models.enums.CreditPackageType;

import java.math.BigDecimal;

public class CreditPackageRequest {

    private CreditPackageType creditPackageType;
    private BigDecimal cost;
    private BigDecimal credits;

    public static CreditPackageRequestBuilder builder() {
        return new CreditPackageRequestBuilder();
    }

    public CreditPackageType getCreditPackageType() {
        return this.creditPackageType;
    }

    public BigDecimal getCost() {
        return this.cost;
    }

    public BigDecimal getCredits() {
        return this.credits;
    }

    public void setCreditPackageType(CreditPackageType creditPackageType) {
        this.creditPackageType = creditPackageType;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public String toString() {
        CreditPackageType var10000 = this.getCreditPackageType();
        return "CreditPackageRequest(creditPackageType=" + var10000 + ", cost=" + this.getCost() + ", credits="
                + this.getCredits() + ")";
    }

    public CreditPackageRequest() {
    }

    public CreditPackageRequest(CreditPackageType creditPackageType, BigDecimal cost, BigDecimal credits) {
        this.creditPackageType = creditPackageType;
        this.cost = cost;
        this.credits = credits;
    }

    public static class CreditPackageRequestBuilder {

        private CreditPackageType creditPackageType;
        private BigDecimal cost;
        private BigDecimal credits;

        CreditPackageRequestBuilder() {
        }

        public CreditPackageRequestBuilder creditPackageType(CreditPackageType creditPackageType) {
            this.creditPackageType = creditPackageType;
            return this;
        }

        public CreditPackageRequestBuilder cost(BigDecimal cost) {
            this.cost = cost;
            return this;
        }

        public CreditPackageRequestBuilder credits(BigDecimal credits) {
            this.credits = credits;
            return this;
        }

        public CreditPackageRequest build() {
            return new CreditPackageRequest(this.creditPackageType, this.cost, this.credits);
        }

        public String toString() {
            return "CreditPackageRequest.CreditPackageRequestBuilder(creditPackageType=" + this.creditPackageType
                    + ", cost=" + this.cost + ", credits=" + this.credits + ")";
        }
    }
}
