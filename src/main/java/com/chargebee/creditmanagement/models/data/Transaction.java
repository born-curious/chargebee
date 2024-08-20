package com.chargebee.creditmanagement.models.data;

import com.chargebee.creditmanagement.models.enums.TransactionStatus;
import com.chargebee.creditmanagement.models.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {

    private String txnId;
    private String userId;
    private TransactionType transactionType;
    private BigDecimal credits;
    private TransactionStatus status;
    private Date creationTimestamp;

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }

    public String getTxnId() {
        return this.txnId;
    }

    public String getUserId() {
        return this.userId;
    }

    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    public BigDecimal getCredits() {
        return this.credits;
    }

    public TransactionStatus getStatus() {
        return this.status;
    }

    public Date getCreationTimestamp() {
        return this.creationTimestamp;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setCredits(BigDecimal credits) {
        this.credits = credits;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String toString() {
        String var10000 = this.getTxnId();
        return "Transaction(txnId=" + var10000 + ", userId=" + this.getUserId() + ", transactionType="
                + this.getTransactionType() + ", credits=" + this.getCredits() + ", status=" + this.getStatus()
                + ", creationTimestamp=" + this.getCreationTimestamp() + ")";
    }

    public Transaction() {
    }

    public Transaction(String txnId, String userId, TransactionType transactionType, BigDecimal credits,
            TransactionStatus status, Date creationTimestamp) {
        this.txnId = txnId;
        this.userId = userId;
        this.transactionType = transactionType;
        this.credits = credits;
        this.status = status;
        this.creationTimestamp = creationTimestamp;
    }

    public static class TransactionBuilder {

        private String txnId;
        private String userId;
        private TransactionType transactionType;
        private BigDecimal credits;
        private TransactionStatus status;
        private Date creationTimestamp;

        TransactionBuilder() {
        }

        public TransactionBuilder txnId(String txnId) {
            this.txnId = txnId;
            return this;
        }

        public TransactionBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public TransactionBuilder transactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public TransactionBuilder credits(BigDecimal credits) {
            this.credits = credits;
            return this;
        }

        public TransactionBuilder status(TransactionStatus status) {
            this.status = status;
            return this;
        }

        public TransactionBuilder creationTimestamp(Date creationTimestamp) {
            this.creationTimestamp = creationTimestamp;
            return this;
        }

        public Transaction build() {
            return new Transaction(this.txnId, this.userId, this.transactionType, this.credits, this.status,
                    this.creationTimestamp);
        }

        public String toString() {
            return "Transaction.TransactionBuilder(txnId=" + this.txnId + ", userId=" + this.userId
                    + ", transactionType=" + this.transactionType + ", credits=" + this.credits + ", status="
                    + this.status + ", creationTimestamp=" + this.creationTimestamp + ")";
        }
    }
}
