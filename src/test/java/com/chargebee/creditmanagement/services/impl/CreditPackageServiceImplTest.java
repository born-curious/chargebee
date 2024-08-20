package com.chargebee.creditmanagement.services.impl;

import com.chargebee.creditmanagement.adaptors.CreditPackageRequestAdaptor;
import com.chargebee.creditmanagement.daos.CreditPackageDao;
import com.chargebee.creditmanagement.daos.impl.CreditPackageDaoImpl;
import com.chargebee.creditmanagement.models.data.CreditPackage;
import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.data.UserCredit;
import com.chargebee.creditmanagement.models.enums.CreditPackageType;
import com.chargebee.creditmanagement.models.enums.TransactionStatus;
import com.chargebee.creditmanagement.models.exceptions.NotFoundException;
import com.chargebee.creditmanagement.models.exceptions.ValidationException;
import com.chargebee.creditmanagement.models.requests.CreditPackageRequest;
import com.chargebee.creditmanagement.models.requests.PurchaseCreditPackageRequest;
import com.chargebee.creditmanagement.services.TransactionService;
import com.chargebee.creditmanagement.services.UserCreditService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreditPackageServiceImplTest {

    private static CreditPackageDao creditPackageDaoMock;
    private static TransactionService transactionServiceMock;
    private static UserCreditService userCreditServiceMock;
    private static CreditPackageRequestAdaptor creditPackageRequestAdaptorMock;
    private static MockedStatic<CreditPackageDaoImpl> creditPackageDaoMockedStatic;
    private static MockedStatic<TransactionServiceImpl> transactionServiceMockedStatic;
    private static MockedStatic<UserCreditServiceImpl> userCreditServiceMockedStatic;
    private static MockedStatic<CreditPackageRequestAdaptor> creditPackageRequestAdaptorMockedStatic;

    @BeforeAll
    public static void setup() {
        creditPackageDaoMock = mock(CreditPackageDaoImpl.class);
        creditPackageDaoMockedStatic = mockStatic(CreditPackageDaoImpl.class);
        creditPackageDaoMockedStatic.when(CreditPackageDaoImpl::getInstance)
                .thenReturn(creditPackageDaoMock);

        transactionServiceMock = mock(TransactionServiceImpl.class);
        transactionServiceMockedStatic = mockStatic(TransactionServiceImpl.class);
        transactionServiceMockedStatic.when(TransactionServiceImpl::getInstance)
                .thenReturn(transactionServiceMock);

        userCreditServiceMock = mock(UserCreditServiceImpl.class);
        userCreditServiceMockedStatic = mockStatic(UserCreditServiceImpl.class);
        userCreditServiceMockedStatic.when(UserCreditServiceImpl::getInstance)
                .thenReturn(userCreditServiceMock);

        creditPackageRequestAdaptorMock = mock(CreditPackageRequestAdaptor.class);
        creditPackageRequestAdaptorMockedStatic = mockStatic(CreditPackageRequestAdaptor.class);
        creditPackageRequestAdaptorMockedStatic.when(CreditPackageRequestAdaptor::getInstance)
                .thenReturn(creditPackageRequestAdaptorMock);
    }

    @AfterAll
    public static void tearDown() {
        creditPackageDaoMockedStatic.close();
        transactionServiceMockedStatic.close();
        userCreditServiceMockedStatic.close();
        creditPackageRequestAdaptorMockedStatic.close();
    }

    @Test
    void testCreateCreditPackage() {
        CreditPackageRequest creditPackageRequest = CreditPackageRequest.builder()
                .creditPackageType(CreditPackageType.BASIC)
                .credits(BigDecimal.valueOf(10))
                .cost(BigDecimal.valueOf(10))
                .build();
        CreditPackage creditPackage = CreditPackage.builder()
                .creditPackageType(CreditPackageType.BASIC)
                .credits(BigDecimal.valueOf(10))
                .cost(BigDecimal.valueOf(10))
                .build();
        when(creditPackageRequestAdaptorMock.apply(creditPackageRequest)).thenReturn(creditPackage);
        when(creditPackageDaoMock.getCreditPackage(creditPackageRequest.getCreditPackageType())).thenThrow(
                new NotFoundException("Credit Package Not Found"));

        CreditPackageServiceImpl instance = CreditPackageServiceImpl.getInstance();

        CreditPackage result = instance.createCreditPackage(creditPackageRequest);

        assertNotNull(result);
        assertEquals(creditPackage, result);
        verify(creditPackageDaoMock).insertCreditPackage(result);
    }

    @Test
    void testUpdateCreditPackage() {
        CreditPackageRequest creditPackageRequest = CreditPackageRequest.builder()
                .creditPackageType(CreditPackageType.BASIC)
                .credits(BigDecimal.valueOf(100))
                .cost(BigDecimal.valueOf(100))
                .build();
        CreditPackage creditPackage = CreditPackage.builder()
                .creditPackageType(CreditPackageType.BASIC)
                .credits(BigDecimal.valueOf(100))
                .cost(BigDecimal.valueOf(100))
                .build();
        when(creditPackageRequestAdaptorMock.apply(creditPackageRequest)).thenReturn(creditPackage);
        when(creditPackageDaoMock.getCreditPackage(creditPackageRequest.getCreditPackageType())).thenReturn(
                creditPackage);

        CreditPackageServiceImpl instance = CreditPackageServiceImpl.getInstance();

        CreditPackage result = instance.updateCreditPackage(creditPackageRequest);

        assertNotNull(result);
        assertEquals(creditPackage, result);
        verify(creditPackageDaoMock).updateCreditPackage(result);
    }

    @Test
    void testUpdateCreditPackage_CreditPackageNotFound() {
        CreditPackageRequest creditPackageRequest = CreditPackageRequest.builder()
                .creditPackageType(CreditPackageType.STANDARD)
                .credits(BigDecimal.valueOf(100))
                .cost(BigDecimal.valueOf(100))
                .build();
        when(creditPackageDaoMock.getCreditPackage(creditPackageRequest.getCreditPackageType())).thenThrow(
                new NotFoundException("Credit Package Not Found"));

        CreditPackageServiceImpl instance = CreditPackageServiceImpl.getInstance();

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> instance.updateCreditPackage(creditPackageRequest));
        assertEquals("Credit Package Not Found", exception.getMessage());
    }

    @Test
    void testUpdateCreditPackage_InvalidRequest() {
        CreditPackageRequest creditPackageRequest = CreditPackageRequest.builder()
                .creditPackageType(null)
                .credits(BigDecimal.valueOf(100))
                .build();
        when(creditPackageDaoMock.getCreditPackage(creditPackageRequest.getCreditPackageType())).thenReturn(null);

        CreditPackageServiceImpl instance = CreditPackageServiceImpl.getInstance();

        assertThrows(ValidationException.class, () -> instance.updateCreditPackage(creditPackageRequest));
        verify(creditPackageDaoMock, times(0)).getCreditPackage(creditPackageRequest.getCreditPackageType());
    }

    @Test
    void testGetCreditPackage() {
        CreditPackageType creditPackageType = CreditPackageType.PREMIUM;
        CreditPackage creditPackage = CreditPackage.builder()
                .creditPackageType(creditPackageType)
                .credits(BigDecimal.valueOf(20))
                .build();
        when(creditPackageDaoMock.getCreditPackage(creditPackageType)).thenReturn(creditPackage);

        CreditPackageServiceImpl instance = CreditPackageServiceImpl.getInstance();

        CreditPackage result = instance.getCreditPackage(creditPackageType);

        assertNotNull(result);
        assertEquals(creditPackage, result);
        verify(creditPackageDaoMock).getCreditPackage(creditPackageType);
    }

    @Test
    void testViewAllPackages() {
        List<CreditPackage> creditPackages = Arrays.asList(
                CreditPackage.builder().creditPackageType(CreditPackageType.BASIC).credits(BigDecimal.valueOf(10))
                        .build(),
                CreditPackage.builder().creditPackageType(CreditPackageType.PREMIUM).credits(BigDecimal.valueOf(20))
                        .build()
        );
        when(creditPackageDaoMock.list()).thenReturn(creditPackages);

        CreditPackageServiceImpl instance = CreditPackageServiceImpl.getInstance();

        List<CreditPackage> result = instance.viewAllPackages();

        assertNotNull(result);
        assertEquals(creditPackages, result);
    }

    @Test
    void testViewAllPackages_EmptyList() {
        when(creditPackageDaoMock.list()).thenReturn(Collections.emptyList());

        CreditPackageServiceImpl instance = CreditPackageServiceImpl.getInstance();

        List<CreditPackage> result = instance.viewAllPackages();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testPurchaseCredits_HappyPath() {
        PurchaseCreditPackageRequest request = PurchaseCreditPackageRequest.builder()
                .userId("userId1")
                .creditPackageType(CreditPackageType.BASIC)
                .build();
        CreditPackage creditPackage = CreditPackage.builder()
                .creditPackageType(CreditPackageType.BASIC)
                .credits(BigDecimal.valueOf(10))
                .build();
        Transaction transaction = Transaction.builder()
                .status(TransactionStatus.ACCEPTED)
                .build();
        UserCredit userCredit = UserCredit.builder()
                .userId("userId1")
                .creditsAvailable(BigDecimal.valueOf(100))
                .build();

        when(creditPackageDaoMock.getCreditPackage(request.getCreditPackageType())).thenReturn(creditPackage);
        when(transactionServiceMock.createTransaction(request, creditPackage.getCredits(), TransactionStatus.ACCEPTED))
                .thenReturn(transaction);
        when(userCreditServiceMock.getUserCreditForInsertOrUpdate(request.getUserId())).thenReturn(userCredit);
        when(userCreditServiceMock.updateUserCredit(userCredit, transaction)).thenReturn(userCredit);

        CreditPackageServiceImpl instance = CreditPackageServiceImpl.getInstance();

        Transaction result = instance.purchaseCredits(request);

        assertNotNull(result);
        assertEquals(transaction, result);
        verify(userCreditServiceMock).updateUserCredit(userCredit, transaction);
    }

    @Test
    void testPurchaseCredits_UserCreditNotFound() {
        PurchaseCreditPackageRequest request = PurchaseCreditPackageRequest.builder()
                .userId("userId2")
                .creditPackageType(CreditPackageType.BASIC)
                .build();
        CreditPackage creditPackage = CreditPackage.builder()
                .creditPackageType(CreditPackageType.BASIC)
                .credits(BigDecimal.valueOf(10))
                .build();
        Transaction transaction = Transaction.builder()
                .status(TransactionStatus.ACCEPTED)
                .build();

        when(creditPackageDaoMock.getCreditPackage(request.getCreditPackageType())).thenReturn(creditPackage);
        when(transactionServiceMock.createTransaction(request, creditPackage.getCredits(), TransactionStatus.ACCEPTED))
                .thenReturn(transaction);
        when(userCreditServiceMock.getUserCreditForInsertOrUpdate(request.getUserId())).thenReturn(null);

        CreditPackageServiceImpl instance = CreditPackageServiceImpl.getInstance();

        Transaction result = instance.purchaseCredits(request);

        assertNotNull(result);
        assertEquals(transaction, result);
        verify(userCreditServiceMock).insertUserCredit(transaction);
    }
}