package com.chargebee.creditmanagement.datastores.impl;

import com.chargebee.creditmanagement.models.data.Transaction;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionDatastore extends ConcurrentHashMap<String, Transaction> {

    private static TransactionDatastore instance = null;

    public static TransactionDatastore getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (TransactionDatastore.class) {
                if (Objects.isNull(instance)) {
                    instance = new TransactionDatastore();
                }
            }
        }
        return instance;
    }

    private TransactionDatastore() {
        super();
    }
}
