package com.chargebee.creditmanagement.daos;

import com.chargebee.creditmanagement.models.data.UserCredit;

public interface UserCreditDao {

    void insertUserCredit(UserCredit userCredit);

    void updateUserCredit(UserCredit userCredit);

    UserCredit getUserCredit(String userId);

    UserCredit getUserCreditForInsertOrUpdate(String userId);

    UserCredit getUserCreditForUpdate(String userId);
}
