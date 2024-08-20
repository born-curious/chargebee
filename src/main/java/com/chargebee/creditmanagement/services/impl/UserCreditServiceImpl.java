package com.chargebee.creditmanagement.services.impl;

import com.chargebee.creditmanagement.daos.UserCreditDao;
import com.chargebee.creditmanagement.daos.impl.UserCreditDaoImpl;
import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.data.UserCredit;
import com.chargebee.creditmanagement.services.UserCreditService;

import java.math.BigDecimal;
import java.util.Objects;

public class UserCreditServiceImpl implements UserCreditService {

    private static UserCreditServiceImpl instance = null;
    private final UserCreditDao userCreditDao;

    @Override
    public UserCredit insertUserCredit(Transaction transaction) {
        UserCredit userCredit = new UserCredit(transaction.getUserId(), transaction.getCredits());
        userCreditDao.insertUserCredit(userCredit);
        return userCredit;
    }

    @Override
    public UserCredit updateUserCredit(UserCredit userCredit, Transaction transaction) {
        BigDecimal credis;
        switch (transaction.getTransactionType()) {
            case PURCHASE:
                credis = userCredit.getCreditsAvailable().add(transaction.getCredits());
                break;
            case USAGE:
                credis = userCredit.getCreditsAvailable().subtract(transaction.getCredits());
                break;
            default:
                throw new RuntimeException(
                        "Operation on Transaction Type not defined - " + transaction.getTransactionType());
        }
        userCredit.setCreditsAvailable(credis);
        userCreditDao.updateUserCredit(userCredit);
        return userCredit;
    }

    @Override
    public UserCredit getUserCredit(String userId) {
        return userCreditDao.getUserCredit(userId);
    }

    @Override
    public UserCredit getUserCreditForInsertOrUpdate(String userId) {
        return userCreditDao.getUserCreditForInsertOrUpdate(userId);
    }

    @Override
    public UserCredit getUserCreditForUpdate(String userId) {
        return userCreditDao.getUserCreditForUpdate(userId);
    }

    public static UserCreditServiceImpl getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (UserCreditServiceImpl.class) {
                if (Objects.isNull(instance)) {
                    instance = new UserCreditServiceImpl();
                }
            }
        }
        return instance;
    }

    private UserCreditServiceImpl() {
        this.userCreditDao = UserCreditDaoImpl.getInstance();
    }
}
