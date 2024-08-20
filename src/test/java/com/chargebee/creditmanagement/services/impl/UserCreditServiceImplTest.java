package com.chargebee.creditmanagement.services.impl;

import com.chargebee.creditmanagement.daos.UserCreditDao;
import com.chargebee.creditmanagement.daos.impl.UserCreditDaoImpl;
import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.data.UserCredit;
import com.chargebee.creditmanagement.models.enums.TransactionType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserCreditServiceImplTest {

    private static UserCreditDao userCreditDaoMock;
    private static MockedStatic<UserCreditDaoImpl> userCreditDaoMockedStatic;

    @BeforeAll
    public static void setup() {
        userCreditDaoMock = mock(UserCreditDaoImpl.class);
        userCreditDaoMockedStatic = mockStatic(UserCreditDaoImpl.class);
        userCreditDaoMockedStatic.when(UserCreditDaoImpl::getInstance)
                .thenReturn(userCreditDaoMock);
    }

    @AfterAll
    public static void tearDown() {
        userCreditDaoMockedStatic.close();
    }

    @Test
    void testInsertUserCredit() {
        Transaction transaction = Transaction.builder()
                .userId("user1")
                .credits(BigDecimal.valueOf(10))
                .build();
        UserCredit userCredit = new UserCredit("user1", BigDecimal.valueOf(10));

        doNothing().when(userCreditDaoMock).insertUserCredit(any());

        UserCreditServiceImpl instance = UserCreditServiceImpl.getInstance();

        UserCredit result = instance.insertUserCredit(transaction);

        assertNotNull(result);
        assertEquals(userCredit.getUserId(), result.getUserId());
        assertEquals(userCredit.getCreditsAvailable(), result.getCreditsAvailable());
        verify(userCreditDaoMock).insertUserCredit(result);
    }

    @Test
    void testUpdateUserCredit_Purchase() {
        UserCredit userCredit = new UserCredit("user1", BigDecimal.valueOf(10));
        Transaction transaction = Transaction.builder()
                .userId("user1")
                .credits(BigDecimal.valueOf(5))
                .transactionType(TransactionType.PURCHASE)
                .build();

        when(userCreditDaoMock.getUserCreditForUpdate("user1")).thenReturn(userCredit);

        UserCreditServiceImpl instance = UserCreditServiceImpl.getInstance();

        UserCredit result = instance.updateUserCredit(userCredit, transaction);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(15), result.getCreditsAvailable());
        verify(userCreditDaoMock).updateUserCredit(result);
    }

    @Test
    void testUpdateUserCredit_Usage() {
        UserCredit userCredit = new UserCredit("user1", BigDecimal.valueOf(10));
        Transaction transaction = Transaction.builder()
                .userId("user1")
                .credits(BigDecimal.valueOf(5))
                .transactionType(TransactionType.USAGE)
                .build();

        when(userCreditDaoMock.getUserCreditForUpdate("user1")).thenReturn(userCredit);

        UserCreditServiceImpl instance = UserCreditServiceImpl.getInstance();

        UserCredit result = instance.updateUserCredit(userCredit, transaction);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(5), result.getCreditsAvailable());
        verify(userCreditDaoMock).updateUserCredit(result);
    }

    @Test
    void testGetUserCredit() {
        UserCredit userCredit = new UserCredit("user1", BigDecimal.valueOf(10));

        when(userCreditDaoMock.getUserCredit("user1")).thenReturn(userCredit);

        UserCreditServiceImpl instance = UserCreditServiceImpl.getInstance();

        UserCredit result = instance.getUserCredit("user1");

        assertNotNull(result);
        assertEquals(userCredit, result);
    }

    @Test
    void testGetUserCreditForUpdate() {
        UserCredit userCredit = new UserCredit("user1", BigDecimal.valueOf(10));

        when(userCreditDaoMock.getUserCreditForUpdate("user1")).thenReturn(userCredit);

        UserCreditServiceImpl instance = UserCreditServiceImpl.getInstance();

        UserCredit result = instance.getUserCreditForUpdate("user1");

        assertNotNull(result);
        assertEquals(userCredit, result);
    }
}