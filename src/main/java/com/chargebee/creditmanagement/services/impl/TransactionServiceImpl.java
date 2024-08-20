package com.chargebee.creditmanagement.services.impl;

import com.chargebee.creditmanagement.adaptors.PurchaseCreditPackageRequestAdaptor;
import com.chargebee.creditmanagement.adaptors.ServiceUsageRequestAdaptor;
import com.chargebee.creditmanagement.daos.TransactionDao;
import com.chargebee.creditmanagement.daos.impl.TransactionDaoImpl;
import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.enums.TransactionStatus;
import com.chargebee.creditmanagement.models.requests.PurchaseCreditPackageRequest;
import com.chargebee.creditmanagement.models.requests.ServiceUsageRequest;
import com.chargebee.creditmanagement.services.TransactionService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class TransactionServiceImpl implements TransactionService {

    private static TransactionServiceImpl instance = null;
    private final TransactionDao transactionDao;
    private final PurchaseCreditPackageRequestAdaptor purchaseCreditPackageRequestAdaptor;
    private final ServiceUsageRequestAdaptor serviceUsageRequestAdaptor;


    @Override
    public Transaction createTransaction(PurchaseCreditPackageRequest purchaseCreditPackageRequest, BigDecimal amount,
            TransactionStatus transactionStatus) {
        Transaction transaction = purchaseCreditPackageRequestAdaptor.apply(purchaseCreditPackageRequest);
        transaction.setCredits(amount);
        transaction.setStatus(transactionStatus);
        transactionDao.insertTransaction(transaction);
        return transaction;
    }

    @Override
    public Transaction createTransaction(ServiceUsageRequest serviceUsageRequest, BigDecimal amount,
            TransactionStatus transactionStatus) {
        Transaction transaction = serviceUsageRequestAdaptor.apply(serviceUsageRequest);
        transaction.setCredits(amount);
        transaction.setStatus(transactionStatus);
        transactionDao.insertTransaction(transaction);
        return transaction;
    }

    @Override
    public Transaction getTransaction(String transactionId) {
        return transactionDao.getTransaction(transactionId);
    }

    @Override
    public List<Transaction> viewAllTransactions(String userId) {
        return transactionDao.getTransactionForUser(userId);
    }

    public static TransactionServiceImpl getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (TransactionServiceImpl.class) {
                if (Objects.isNull(instance)) {
                    instance = new TransactionServiceImpl();
                }
            }
        }
        return instance;
    }

    private TransactionServiceImpl() {
        this.transactionDao = TransactionDaoImpl.getInstance();
        this.purchaseCreditPackageRequestAdaptor = PurchaseCreditPackageRequestAdaptor.getInstance();
        this.serviceUsageRequestAdaptor = ServiceUsageRequestAdaptor.getInstance();
    }
}
