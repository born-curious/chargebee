package com.chargebee.creditmanagement.daos.impl;

import com.chargebee.creditmanagement.daos.UserCreditDao;
import com.chargebee.creditmanagement.datastores.impl.UserCreditDatastore;
import com.chargebee.creditmanagement.models.data.UserCredit;
import com.chargebee.creditmanagement.models.exceptions.NotFoundException;

import java.util.Objects;

public class UserCreditDaoImpl implements UserCreditDao {

    private static UserCreditDaoImpl instance = null;
    private final UserCreditDatastore userCreditDatastore;

    @Override
    public void insertUserCredit(UserCredit userCredit) {
        userCreditDatastore.insertLockedValue(userCredit.getUserId(), userCredit);
    }

    @Override
    public void updateUserCredit(UserCredit userCredit) {
        userCreditDatastore.updateLockedValue(userCredit.getUserId(), userCredit);
    }

    @Override
    public UserCredit getUserCredit(String userId) {
        UserCredit userCredit = userCreditDatastore.get(userId);
        if (Objects.isNull(userCredit)) {
            throw new NotFoundException("User Credit Not Found for UserId - " + userId);
        }
        return userCredit;
    }

    @Override
    public UserCredit getUserCreditForInsertOrUpdate(String userId) {
        return userCreditDatastore.getForUpdate(userId);
    }

    @Override
    public UserCredit getUserCreditForUpdate(String userId) {
        UserCredit userCredit = userCreditDatastore.getForUpdate(userId);
        if (Objects.isNull(userCredit)) {
            userCreditDatastore.releaseLockedKey(userId);
            throw new NotFoundException("User Credit Not Found for UserId - " + userId);
        }
        return userCredit;
    }

    public static UserCreditDaoImpl getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (UserCreditDaoImpl.class) {
                if (Objects.isNull(instance)) {
                    instance = new UserCreditDaoImpl();
                }
            }
        }
        return instance;
    }

    private UserCreditDaoImpl() {
        this.userCreditDatastore = UserCreditDatastore.getInstance();
    }
}
