package com.chargebee.creditmanagement.daos.impl;

import com.chargebee.creditmanagement.daos.TransactionDao;
import com.chargebee.creditmanagement.datastores.impl.TransactionDatastore;
import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.exceptions.NotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TransactionDaoImpl implements TransactionDao {

    private static TransactionDaoImpl instance = null;
    private final TransactionDatastore transactionDatastore;

    @Override
    public void insertTransaction(Transaction transaction) {
        transactionDatastore.put(transaction.getTxnId(), transaction);
    }

    @Override
    public Transaction getTransaction(String transactionId) {
        Transaction transaction = transactionDatastore.get(transactionId);
        if (Objects.isNull(transaction)) {
            throw new NotFoundException("Transaction Not Found for TransactionId - " + transactionId);
        }
        return transaction;
    }

    @Override
    public List<Transaction> getTransactionForUser(String userId) {
        return transactionDatastore.values().stream()
                .filter(transaction -> userId.equals(transaction.getUserId()))
                .sorted(Comparator.comparing(Transaction::getCreationTimestamp))
                .collect(Collectors.toList());
    }

    public static TransactionDaoImpl getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (TransactionDaoImpl.class) {
                if (Objects.isNull(instance)) {
                    instance = new TransactionDaoImpl();
                }
            }
        }
        return instance;
    }

    private TransactionDaoImpl() {
        this.transactionDatastore = TransactionDatastore.getInstance();
    }
}
