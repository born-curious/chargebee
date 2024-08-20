package com.chargebee.creditmanagement.models.data;

import com.chargebee.creditmanagement.models.enums.CreditPackageType;

import java.math.BigDecimal;

public class CreditPackage {

    private CreditPackageType creditPackageType;
    private BigDecimal cost;
    private BigDecimal credits;

    public static CreditPackageBuilder builder() {
        return new CreditPackageBuilder();
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
        return "CreditPackage(creditPackageType=" + var10000 + ", cost=" + this.getCost() + ", credits="
                + this.getCredits() + ")";
    }

    public CreditPackage() {
    }

    public CreditPackage(CreditPackageType creditPackageType, BigDecimal cost, BigDecimal credits) {
        this.creditPackageType = creditPackageType;
        this.cost = cost;
        this.credits = credits;
    }

    public static class CreditPackageBuilder {

        private CreditPackageType creditPackageType;
        private BigDecimal cost;
        private BigDecimal credits;

        CreditPackageBuilder() {
        }

        public CreditPackageBuilder creditPackageType(CreditPackageType creditPackageType) {
            this.creditPackageType = creditPackageType;
            return this;
        }

        public CreditPackageBuilder cost(BigDecimal cost) {
            this.cost = cost;
            return this;
        }

        public CreditPackageBuilder credits(BigDecimal credits) {
            this.credits = credits;
            return this;
        }

        public CreditPackage build() {
            return new CreditPackage(this.creditPackageType, this.cost, this.credits);
        }

        public String toString() {
            return "CreditPackage.CreditPackageBuilder(creditPackageType=" + this.creditPackageType + ", cost="
                    + this.cost + ", credits=" + this.credits + ")";
        }
    }
}
