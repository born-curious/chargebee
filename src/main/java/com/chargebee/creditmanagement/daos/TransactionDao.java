package com.chargebee.creditmanagement.daos;

import com.chargebee.creditmanagement.models.data.Transaction;

import java.util.List;

public interface TransactionDao {

    void insertTransaction(Transaction transaction);

    Transaction getTransaction(String transactionId);

    List<Transaction> getTransactionForUser(String userId);
}
