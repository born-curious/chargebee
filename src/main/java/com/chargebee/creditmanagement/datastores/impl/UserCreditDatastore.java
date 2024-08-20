package com.chargebee.creditmanagement.datastores.impl;

import com.chargebee.creditmanagement.datastores.LockableConcurrentHashMap;
import com.chargebee.creditmanagement.models.data.UserCredit;

import java.util.Objects;

public class UserCreditDatastore extends LockableConcurrentHashMap<String, UserCredit> {

    private static UserCreditDatastore instance = null;

    public static UserCreditDatastore getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (UserCreditDatastore.class) {
                if (Objects.isNull(instance)) {
                    instance = new UserCreditDatastore();
                }
            }
        }
        return instance;
    }

    private UserCreditDatastore() {
        super();
    }
}
