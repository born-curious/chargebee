package com.chargebee.creditmanagement.models.data;

import java.math.BigDecimal;

public class UserCredit {

    private String userId;
    private BigDecimal creditsAvailable;

    public static UserCreditBuilder builder() {
        return new UserCreditBuilder();
    }

    public String getUserId() {
        return this.userId;
    }

    public BigDecimal getCreditsAvailable() {
        return this.creditsAvailable;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCreditsAvailable(BigDecimal creditsAvailable) {
        this.creditsAvailable = creditsAvailable;
    }

    public String toString() {
        String var10000 = this.getUserId();
        return "UserCredit(userId=" + var10000 + ", creditsAvailable=" + this.getCreditsAvailable() + ")";
    }

    public UserCredit() {
    }

    public UserCredit(String userId, BigDecimal creditsAvailable) {
        this.userId = userId;
        this.creditsAvailable = creditsAvailable;
    }

    public static class UserCreditBuilder {

        private String userId;
        private BigDecimal creditsAvailable;

        UserCreditBuilder() {
        }

        public UserCreditBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserCreditBuilder creditsAvailable(BigDecimal creditsAvailable) {
            this.creditsAvailable = creditsAvailable;
            return this;
        }

        public UserCredit build() {
            return new UserCredit(this.userId, this.creditsAvailable);
        }

        public String toString() {
            return "UserCredit.UserCreditBuilder(userId=" + this.userId + ", creditsAvailable=" + this.creditsAvailable
                    + ")";
        }
    }
}
