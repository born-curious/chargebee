package com.chargebee.creditmanagement.daos.impl;

import com.chargebee.creditmanagement.datastores.impl.TransactionDatastore;
import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionDaoImplTest {

    private static TransactionDatastore transactionDatastoreMock;
    private static MockedStatic<TransactionDatastore> transactionDatastoreMockedStatic;

    @BeforeAll
    public static void setup() {
        transactionDatastoreMock = mock(TransactionDatastore.class);
        transactionDatastoreMockedStatic = mockStatic(TransactionDatastore.class);
        transactionDatastoreMockedStatic.when(TransactionDatastore::getInstance)
                .thenReturn(transactionDatastoreMock);
    }

    @AfterAll
    public static void tearDown() {
        transactionDatastoreMockedStatic.close();
    }

    @Test
    void testInsertTransaction() {
        Transaction transaction = mock(Transaction.class);
        when(transaction.getTxnId()).thenReturn("txnId");

        TransactionDaoImpl instance = TransactionDaoImpl.getInstance();

        instance.insertTransaction(transaction);

        verify(transactionDatastoreMock).put("txnId", transaction);
    }

    @Test
    void testGetTransaction() {
        Transaction transaction = mock(Transaction.class);
        when(transactionDatastoreMock.get("txnId")).thenReturn(transaction);

        TransactionDaoImpl instance = TransactionDaoImpl.getInstance();

        Transaction result = instance.getTransaction("txnId");

        assertEquals(transaction, result);
    }

    @Test
    void testGetTransaction_NotFound() {
        TransactionDaoImpl instance = TransactionDaoImpl.getInstance();

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> instance.getTransaction("txnId_notFound"));
        assertEquals("Transaction Not Found for TransactionId - txnId_notFound", exception.getMessage());
    }

    @Test
    void testGetTransactionForUser() {
        Date now = new Date();
        Transaction transaction1 = mock(Transaction.class);
        when(transaction1.getUserId()).thenReturn("userId");
        when(transaction1.getCreationTimestamp()).thenReturn(now);

        Date yesterday = new Date(now.getTime() - (1000 * 60 * 60 * 24));
        Transaction transaction2 = mock(Transaction.class);
        when(transaction2.getUserId()).thenReturn("userId");
        when(transaction2.getCreationTimestamp()).thenReturn(yesterday);

        List<Transaction> transactions = List.of(transaction1, transaction2);
        when(transactionDatastoreMock.values()).thenReturn(transactions);

        TransactionDaoImpl instance = TransactionDaoImpl.getInstance();

        List<Transaction> result = instance.getTransactionForUser("userId");

        assertEquals(List.of(transaction2, transaction1), result);
    }
}
