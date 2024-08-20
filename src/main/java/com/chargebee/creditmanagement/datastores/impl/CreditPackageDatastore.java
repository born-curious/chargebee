package com.chargebee.creditmanagement.datastores.impl;

import com.chargebee.creditmanagement.models.data.CreditPackage;
import com.chargebee.creditmanagement.models.enums.CreditPackageType;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class CreditPackageDatastore extends ConcurrentHashMap<CreditPackageType, CreditPackage> {

    private static CreditPackageDatastore instance = null;

    public static CreditPackageDatastore getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (CreditPackageDatastore.class) {
                if (Objects.isNull(instance)) {
                    instance = new CreditPackageDatastore();
                }
            }
        }
        return instance;
    }

    private CreditPackageDatastore() {
        super();
    }
}
