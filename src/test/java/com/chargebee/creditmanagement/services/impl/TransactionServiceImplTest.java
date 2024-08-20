package com.chargebee.creditmanagement.services.impl;

import com.chargebee.creditmanagement.adaptors.PurchaseCreditPackageRequestAdaptor;
import com.chargebee.creditmanagement.adaptors.ServiceUsageRequestAdaptor;
import com.chargebee.creditmanagement.daos.TransactionDao;
import com.chargebee.creditmanagement.daos.impl.TransactionDaoImpl;
import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.enums.TransactionStatus;
import com.chargebee.creditmanagement.models.requests.PurchaseCreditPackageRequest;
import com.chargebee.creditmanagement.models.requests.ServiceUsageRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    private static TransactionDao transactionDaoMock;
    private static PurchaseCreditPackageRequestAdaptor purchaseCreditPackageRequestAdaptorMock;
    private static ServiceUsageRequestAdaptor serviceUsageRequestAdaptorMock;
    private static MockedStatic<TransactionDaoImpl> transactionDaoMockedStatic;
    private static MockedStatic<PurchaseCreditPackageRequestAdaptor> purchaseCreditPackageRequestAdaptorMockedStatic;
    private static MockedStatic<ServiceUsageRequestAdaptor> serviceUsageRequestAdaptorMockedStatic;

    @BeforeAll
    public static void setup() {
        transactionDaoMock = mock(TransactionDaoImpl.class);
        transactionDaoMockedStatic = mockStatic(TransactionDaoImpl.class);
        transactionDaoMockedStatic.when(TransactionDaoImpl::getInstance).thenReturn(transactionDaoMock);

        purchaseCreditPackageRequestAdaptorMock = mock(PurchaseCreditPackageRequestAdaptor.class);
        purchaseCreditPackageRequestAdaptorMockedStatic = mockStatic(PurchaseCreditPackageRequestAdaptor.class);
        purchaseCreditPackageRequestAdaptorMockedStatic.when(PurchaseCreditPackageRequestAdaptor::getInstance)
                .thenReturn(purchaseCreditPackageRequestAdaptorMock);

        serviceUsageRequestAdaptorMock = mock(ServiceUsageRequestAdaptor.class);
        serviceUsageRequestAdaptorMockedStatic = mockStatic(ServiceUsageRequestAdaptor.class);
        serviceUsageRequestAdaptorMockedStatic.when(ServiceUsageRequestAdaptor::getInstance)
                .thenReturn(serviceUsageRequestAdaptorMock);

    }

    @AfterAll
    public static void tearDown() {
        transactionDaoMockedStatic.close();
        purchaseCreditPackageRequestAdaptorMockedStatic.close();
        serviceUsageRequestAdaptorMockedStatic.close();
    }

    @Test
    void testCreateTransaction_PurchaseCreditPackageRequest() {
        PurchaseCreditPackageRequest purchaseCreditPackageRequest = PurchaseCreditPackageRequest.builder()
                .userId("user1")
                .build();
        Transaction transaction = Transaction.builder()
                .userId("user1")
                .build();
        when(purchaseCreditPackageRequestAdaptorMock.apply(purchaseCreditPackageRequest)).thenReturn(transaction);

        TransactionServiceImpl instance = TransactionServiceImpl.getInstance();

        Transaction result = instance.createTransaction(purchaseCreditPackageRequest, BigDecimal.valueOf(10),
                TransactionStatus.ACCEPTED);

        assertNotNull(result);
        assertEquals(transaction, result);
        verify(transactionDaoMock).insertTransaction(result);
    }

    @Test
    void testCreateTransaction_ServiceUsageRequest() {
        ServiceUsageRequest serviceUsageRequest = ServiceUsageRequest.builder()
                .userId("user1")
                .build();
        Transaction transaction = Transaction.builder()
                .userId("user1")
                .build();
        when(serviceUsageRequestAdaptorMock.apply(serviceUsageRequest)).thenReturn(transaction);

        TransactionServiceImpl instance = TransactionServiceImpl.getInstance();

        Transaction result = instance.createTransaction(serviceUsageRequest, BigDecimal.valueOf(10),
                TransactionStatus.ACCEPTED);

        assertNotNull(result);
        assertEquals(transaction, result);
        verify(transactionDaoMock).insertTransaction(result);
    }

    @Test
    void testGetTransaction() {
        Transaction transaction = Transaction.builder()
                .txnId("txnId")
                .build();
        when(transactionDaoMock.getTransaction("txnId")).thenReturn(transaction);

        TransactionServiceImpl instance = TransactionServiceImpl.getInstance();

        Transaction result = instance.getTransaction("txnId");

        assertNotNull(result);
        assertEquals(transaction, result);
    }

    @Test
    void testViewAllTransactions() {
        List<Transaction> transactions = List.of(Transaction.builder()
                .userId("user1")
                .build(), Transaction.builder()
                .userId("user1")
                .build());
        when(transactionDaoMock.getTransactionForUser("user1")).thenReturn(transactions);

        TransactionServiceImpl instance = TransactionServiceImpl.getInstance();

        List<Transaction> result = instance.viewAllTransactions("user1");

        assertNotNull(result);
        assertEquals(transactions, result);
    }
}
