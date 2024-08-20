package com.chargebee.creditmanagement.services;

import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.enums.TransactionStatus;
import com.chargebee.creditmanagement.models.requests.PurchaseCreditPackageRequest;
import com.chargebee.creditmanagement.models.requests.ServiceUsageRequest;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    Transaction createTransaction(PurchaseCreditPackageRequest purchaseCreditPackageRequest, BigDecimal amount,
            TransactionStatus transactionStatus);

    Transaction createTransaction(ServiceUsageRequest serviceUsageRequest, BigDecimal amount,
            TransactionStatus transactionStatus);

    Transaction getTransaction(String transactionId);

    List<Transaction> viewAllTransactions(String userId);
}
