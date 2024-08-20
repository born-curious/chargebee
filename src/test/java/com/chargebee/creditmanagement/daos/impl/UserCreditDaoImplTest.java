package com.chargebee.creditmanagement.daos.impl;

import com.chargebee.creditmanagement.datastores.impl.UserCreditDatastore;
import com.chargebee.creditmanagement.models.data.UserCredit;
import com.chargebee.creditmanagement.models.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserCreditDaoImplTest {

    private static UserCreditDatastore userCreditDatastoreMock;
    private static MockedStatic<UserCreditDatastore> userCreditDatastoreMockedStatic;

    @BeforeAll
    public static void setup() {
        userCreditDatastoreMock = mock(UserCreditDatastore.class);
        userCreditDatastoreMockedStatic = mockStatic(UserCreditDatastore.class);
        userCreditDatastoreMockedStatic.when(UserCreditDatastore::getInstance)
                .thenReturn(userCreditDatastoreMock);
    }

    @AfterAll
    public static void tearDown() {
        userCreditDatastoreMockedStatic.close();
    }

    @Test
    void testInsertUserCredit() {
        UserCredit userCredit = mock(UserCredit.class);
        when(userCredit.getUserId()).thenReturn("userId");

        UserCreditDaoImpl instance = UserCreditDaoImpl.getInstance();

        instance.insertUserCredit(userCredit);

        verify(userCreditDatastoreMock).insertLockedValue("userId", userCredit);
    }

    @Test
    void testUpdateUserCredit() {
        UserCredit userCredit = mock(UserCredit.class);
        when(userCredit.getUserId()).thenReturn("userId");

        UserCreditDaoImpl instance = UserCreditDaoImpl.getInstance();

        instance.updateUserCredit(userCredit);

        verify(userCreditDatastoreMock).updateLockedValue("userId", userCredit);
    }

    @Test
    void testGetUserCredit() {
        UserCredit userCredit = mock(UserCredit.class);
        when(userCreditDatastoreMock.get("userId")).thenReturn(userCredit);

        UserCreditDaoImpl instance = UserCreditDaoImpl.getInstance();

        UserCredit result = instance.getUserCredit("userId");

        assertEquals(userCredit, result);
    }

    @Test
    void testGetUserCredit_NotFound() {
        UserCreditDaoImpl instance = UserCreditDaoImpl.getInstance();

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> instance.getUserCredit("userId_notFound"));
        assertEquals("User Credit Not Found for UserId - userId_notFound", exception.getMessage());
    }

    @Test
    void testGetUserCreditForInsertOrUpdate() {
        UserCredit userCredit = mock(UserCredit.class);
        when(userCreditDatastoreMock.getForUpdate("userId")).thenReturn(userCredit);

        UserCreditDaoImpl instance = UserCreditDaoImpl.getInstance();

        UserCredit result = instance.getUserCreditForInsertOrUpdate("userId");

        assertEquals(userCredit, result);
    }

    @Test
    void testGetUserCreditForInsertOrUpdate_null() {
        when(userCreditDatastoreMock.getForUpdate("userId")).thenReturn(null);

        UserCreditDaoImpl instance = UserCreditDaoImpl.getInstance();

        UserCredit result = instance.getUserCreditForInsertOrUpdate("userId");

        assertNull(result);
    }

    @Test
    void testGetUserCreditForUpdate() {
        UserCredit userCredit = mock(UserCredit.class);
        when(userCreditDatastoreMock.getForUpdate("userId")).thenReturn(userCredit);

        UserCreditDaoImpl instance = UserCreditDaoImpl.getInstance();

        UserCredit result = instance.getUserCreditForUpdate("userId");

        assertEquals(userCredit, result);
    }

    @Test
    void testGetUserCreditForUpdate_NotFound() {
        UserCreditDaoImpl instance = UserCreditDaoImpl.getInstance();

        when(userCreditDatastoreMock.getForUpdate("userId")).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> instance.getUserCreditForUpdate("userId"));
        assertEquals("User Credit Not Found for UserId - userId", exception.getMessage());

        verify(userCreditDatastoreMock).releaseLockedKey("userId");
    }
}
