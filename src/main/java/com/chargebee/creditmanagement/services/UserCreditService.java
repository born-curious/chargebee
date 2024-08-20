package com.chargebee.creditmanagement.services;

import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.data.UserCredit;

public interface UserCreditService {

    UserCredit insertUserCredit(Transaction transaction);

    UserCredit updateUserCredit(UserCredit userCredit, Transaction transaction);

    UserCredit getUserCredit(String userId);

    UserCredit getUserCreditForInsertOrUpdate(String userId);

    UserCredit getUserCreditForUpdate(String userId);

}
