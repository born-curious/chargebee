package com.chargebee.creditmanagement.services.impl;

import com.chargebee.creditmanagement.adaptors.CreateServicePricingRequestAdaptor;
import com.chargebee.creditmanagement.adaptors.UpdateServicePricingRequestAdaptor;
import com.chargebee.creditmanagement.daos.ServicePricingDao;
import com.chargebee.creditmanagement.daos.impl.ServicePricingDaoImpl;
import com.chargebee.creditmanagement.models.data.ServicePricing;
import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.data.UserCredit;
import com.chargebee.creditmanagement.models.enums.TransactionStatus;
import com.chargebee.creditmanagement.models.exceptions.NotFoundException;
import com.chargebee.creditmanagement.models.requests.CreateServicePricingRequest;
import com.chargebee.creditmanagement.models.requests.ServiceUsageRequest;
import com.chargebee.creditmanagement.models.requests.UpdateServicePricingRequest;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServicePricingServiceImplTest {

    private static ServicePricingDao servicePricingDaoMock;
    private static TransactionService transactionServiceMock;
    private static UserCreditService userCreditServiceMock;
    private static CreateServicePricingRequestAdaptor createServicePricingRequestAdaptorMock;
    private static UpdateServicePricingRequestAdaptor updateServicePricingRequestAdaptorMock;
    private static MockedStatic<ServicePricingDaoImpl> servicePricingDaoMockedStatic;
    private static MockedStatic<TransactionServiceImpl> transactionServiceMockedStatic;
    private static MockedStatic<UserCreditServiceImpl> userCreditServiceMockedStatic;
    private static MockedStatic<CreateServicePricingRequestAdaptor> createServicePricingRequestAdaptorMockedStatic;
    private static MockedStatic<UpdateServicePricingRequestAdaptor> updateServicePricingRequestAdaptorMockedStatic;

    @BeforeAll
    public static void setup() {
        servicePricingDaoMock = mock(ServicePricingDaoImpl.class);
        servicePricingDaoMockedStatic = mockStatic(ServicePricingDaoImpl.class);
        servicePricingDaoMockedStatic.when(ServicePricingDaoImpl::getInstance)
                .thenReturn(servicePricingDaoMock);

        transactionServiceMock = mock(TransactionServiceImpl.class);
        transactionServiceMockedStatic = mockStatic(TransactionServiceImpl.class);
        transactionServiceMockedStatic.when(TransactionServiceImpl::getInstance)
                .thenReturn(transactionServiceMock);

        userCreditServiceMock = mock(UserCreditServiceImpl.class);
        userCreditServiceMockedStatic = mockStatic(UserCreditServiceImpl.class);
        userCreditServiceMockedStatic.when(UserCreditServiceImpl::getInstance)
                .thenReturn(userCreditServiceMock);

        createServicePricingRequestAdaptorMock = mock(CreateServicePricingRequestAdaptor.class);
        createServicePricingRequestAdaptorMockedStatic = mockStatic(CreateServicePricingRequestAdaptor.class);
        createServicePricingRequestAdaptorMockedStatic.when(CreateServicePricingRequestAdaptor::getInstance)
                .thenReturn(createServicePricingRequestAdaptorMock);

        updateServicePricingRequestAdaptorMock = mock(UpdateServicePricingRequestAdaptor.class);
        updateServicePricingRequestAdaptorMockedStatic = mockStatic(UpdateServicePricingRequestAdaptor.class);
        updateServicePricingRequestAdaptorMockedStatic.when(UpdateServicePricingRequestAdaptor::getInstance)
                .thenReturn(updateServicePricingRequestAdaptorMock);
    }

    @AfterAll
    public static void tearDown() {
        servicePricingDaoMockedStatic.close();
        transactionServiceMockedStatic.close();
        userCreditServiceMockedStatic.close();
        createServicePricingRequestAdaptorMockedStatic.close();
        updateServicePricingRequestAdaptorMockedStatic.close();
    }

    @Test
    void testCreateServicePricing() {
        CreateServicePricingRequest createServicePricingRequest = CreateServicePricingRequest.builder()
                .serviceName("name")
                .usageCost(BigDecimal.valueOf(10))
                .build();
        ServicePricing servicePricing = ServicePricing.builder()
                .serviceId("name")
                .build();
        when(createServicePricingRequestAdaptorMock.apply(createServicePricingRequest)).thenReturn(servicePricing);

        ServicePricingServiceImpl instance = ServicePricingServiceImpl.getInstance();

        ServicePricing result = instance.createServicePricing(createServicePricingRequest);

        assertNotNull(result);
        assertEquals(servicePricing, result);
        verify(servicePricingDaoMock).insertServicePricing(result);
    }

    @Test
    void testUpdateServicePricing() {
        UpdateServicePricingRequest updateServicePricingRequest = UpdateServicePricingRequest.builder()
                .serviceId("serviceId1")
                .usageCost(BigDecimal.valueOf(10))
                .build();
        ServicePricing servicePricing = ServicePricing.builder()
                .serviceId("serviceId1")
                .usageCost(BigDecimal.valueOf(10))
                .build();
        when(updateServicePricingRequestAdaptorMock.apply(updateServicePricingRequest)).thenReturn(servicePricing);
        when(servicePricingDaoMock.getServicePricing(updateServicePricingRequest.getServiceId())).thenReturn(
                servicePricing);

        ServicePricingServiceImpl instance = ServicePricingServiceImpl.getInstance();

        ServicePricing result = instance.updateServicePricing(updateServicePricingRequest);

        assertNotNull(result);
        assertEquals(servicePricing, result);
        verify(servicePricingDaoMock).insertServicePricing(result);
    }

    @Test
    void testGetServicePricing() {
        String serviceId = "serviceId2";
        ServicePricing servicePricing = ServicePricing.builder()
                .serviceId(serviceId)
                .build();
        when(servicePricingDaoMock.getServicePricing(serviceId)).thenReturn(servicePricing);

        ServicePricingServiceImpl instance = ServicePricingServiceImpl.getInstance();

        ServicePricing result = instance.getServicePricing(serviceId);

        assertNotNull(result);
        assertEquals(servicePricing, result);
    }

    @Test
    void testViewAllServicePricing() {
        List<ServicePricing> servicePricings = Arrays.asList(
                ServicePricing.builder().serviceId("serviceId3").build(),
                ServicePricing.builder().serviceId("serviceId4").build()
        );
        when(servicePricingDaoMock.list()).thenReturn(servicePricings);

        ServicePricingServiceImpl instance = ServicePricingServiceImpl.getInstance();

        List<ServicePricing> result = instance.viewAllServicePricing();

        assertNotNull(result);
        assertEquals(servicePricings, result);
    }

    @Test
    void testViewAllServicePricing_NoServicePricings() {
        when(servicePricingDaoMock.list()).thenReturn(Collections.emptyList());

        ServicePricingServiceImpl instance = ServicePricingServiceImpl.getInstance();

        List<ServicePricing> result = instance.viewAllServicePricing();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testUtilizeService_ValidRequest_Accepted() {
        ServiceUsageRequest serviceUsageRequest = ServiceUsageRequest.builder()
                .serviceId("serviceId5")
                .userId("userId1")
                .build();
        ServicePricing servicePricing = ServicePricing.builder()
                .serviceId("serviceId5")
                .usageCost(BigDecimal.valueOf(10))
                .build();
        UserCredit userCredit = UserCredit.builder()
                .userId("userId1")
                .creditsAvailable(BigDecimal.valueOf(20))
                .build();
        when(servicePricingDaoMock.getServicePricing(serviceUsageRequest.getServiceId())).thenReturn(servicePricing);
        when(userCreditServiceMock.getUserCreditForUpdate(serviceUsageRequest.getUserId())).thenReturn(userCredit);
        when(transactionServiceMock.createTransaction(any(ServiceUsageRequest.class), any(BigDecimal.class),
                any(TransactionStatus.class))).thenReturn(
                Transaction.builder().status(TransactionStatus.ACCEPTED).build());

        ServicePricingServiceImpl instance = ServicePricingServiceImpl.getInstance();

        Transaction result = instance.utilizeService(serviceUsageRequest);

        assertEquals(TransactionStatus.ACCEPTED, result.getStatus());
        verify(userCreditServiceMock, times(1)).updateUserCredit(userCredit, result);
    }

    @Test
    void testUtilizeService_ValidRequest_Rejected() {
        ServiceUsageRequest serviceUsageRequest = ServiceUsageRequest.builder()
                .serviceId("serviceId6")
                .userId("userId2")
                .build();
        ServicePricing servicePricing = ServicePricing.builder()
                .serviceId("serviceId6")
                .usageCost(BigDecimal.valueOf(20))
                .build();
        UserCredit userCredit = UserCredit.builder()
                .userId("userId2")
                .creditsAvailable(BigDecimal.valueOf(10))
                .build();
        when(servicePricingDaoMock.getServicePricing(serviceUsageRequest.getServiceId())).thenReturn(servicePricing);
        when(userCreditServiceMock.getUserCreditForUpdate(serviceUsageRequest.getUserId())).thenReturn(userCredit);
        when(transactionServiceMock.createTransaction(any(ServiceUsageRequest.class), any(BigDecimal.class),
                any(TransactionStatus.class))).thenReturn(
                Transaction.builder().status(TransactionStatus.REJECTED).build());

        ServicePricingServiceImpl instance = ServicePricingServiceImpl.getInstance();

        Transaction result = instance.utilizeService(serviceUsageRequest);

        assertEquals(TransactionStatus.REJECTED, result.getStatus());
        verify(userCreditServiceMock, times(0)).updateUserCredit(userCredit, result);
    }

    @Test
    void testUtilizeService_ServicePricingNotFound() {
        ServiceUsageRequest serviceUsageRequest = ServiceUsageRequest.builder()
                .serviceId("serviceId7")
                .userId("userId3")
                .build();
        when(servicePricingDaoMock.getServicePricing(serviceUsageRequest.getServiceId())).thenThrow(
                new NotFoundException("Service Pricing Not Found for ServiceId - serviceId"));

        ServicePricingServiceImpl instance = ServicePricingServiceImpl.getInstance();

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> instance.utilizeService(serviceUsageRequest));
        assertEquals("Service Pricing Not Found for ServiceId - serviceId", exception.getMessage());
    }

    @Test
    void testUtilizeService_UserCreditNotFound() {
        ServiceUsageRequest serviceUsageRequest = ServiceUsageRequest.builder()
                .serviceId("serviceId8")
                .userId("userId4")
                .build();
        ServicePricing servicePricing = ServicePricing.builder()
                .serviceId("serviceId8")
                .usageCost(BigDecimal.valueOf(10))
                .build();
        when(servicePricingDaoMock.getServicePricing(serviceUsageRequest.getServiceId())).thenReturn(servicePricing);
        when(userCreditServiceMock.getUserCreditForUpdate(serviceUsageRequest.getUserId())).thenThrow(
                new NotFoundException("User Credit Not Found for UserId - userId"));

        ServicePricingServiceImpl instance = ServicePricingServiceImpl.getInstance();

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> instance.utilizeService(serviceUsageRequest));
        assertEquals("User Credit Not Found for UserId - userId", exception.getMessage());
    }
}