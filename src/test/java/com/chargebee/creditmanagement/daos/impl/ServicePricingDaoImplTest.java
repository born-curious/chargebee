package com.chargebee.creditmanagement.daos.impl;

import com.chargebee.creditmanagement.datastores.impl.ServicePricingDatastore;
import com.chargebee.creditmanagement.models.data.ServicePricing;
import com.chargebee.creditmanagement.models.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServicePricingDaoImplTest {

    private static ServicePricingDatastore servicePricingDatastoreMock;
    private static MockedStatic<ServicePricingDatastore> servicePricingDatastoreMockedStatic;

    @BeforeAll
    public static void setup() {
        servicePricingDatastoreMock = mock(ServicePricingDatastore.class);
        servicePricingDatastoreMockedStatic = mockStatic(ServicePricingDatastore.class);
        servicePricingDatastoreMockedStatic.when(ServicePricingDatastore::getInstance)
                .thenReturn(servicePricingDatastoreMock);
    }

    @AfterAll
    public static void tearDown() {
        servicePricingDatastoreMockedStatic.close();
    }

    @Test
    void testInsertServicePricing() {
        ServicePricing servicePricing = mock(ServicePricing.class);
        when(servicePricing.getServiceId()).thenReturn("serviceId");

        ServicePricingDaoImpl instance = ServicePricingDaoImpl.getInstance();

        instance.insertServicePricing(servicePricing);

        verify(servicePricingDatastoreMock).put("serviceId", servicePricing);
    }

    @Test
    void testUpdateServicePricing() {
        ServicePricing servicePricing = mock(ServicePricing.class);
        when(servicePricing.getServiceId()).thenReturn("serviceId");

        ServicePricingDaoImpl instance = ServicePricingDaoImpl.getInstance();

        instance.updateServicePricing(servicePricing);

        verify(servicePricingDatastoreMock).put("serviceId", servicePricing);
    }

    @Test
    void testGetServicePricing() {
        ServicePricing servicePricing = mock(ServicePricing.class);
        when(servicePricingDatastoreMock.get("serviceId")).thenReturn(servicePricing);

        ServicePricingDaoImpl instance = ServicePricingDaoImpl.getInstance();

        ServicePricing result = instance.getServicePricing("serviceId");

        assertEquals(servicePricing, result);
    }

    @Test
    void testGetServicePricing_NotFound() {
        ServicePricingDaoImpl instance = ServicePricingDaoImpl.getInstance();

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> instance.getServicePricing("serviceId"));
        assertEquals("Service Pricing Not Found for ServiceId - serviceId", exception.getMessage());
    }

    @Test
    void testList() {
        List<ServicePricing> servicePricings = new ArrayList<>();
        servicePricings.add(mock(ServicePricing.class));
        servicePricings.add(mock(ServicePricing.class));

        when(servicePricingDatastoreMock.values()).thenReturn(servicePricings);

        ServicePricingDaoImpl instance = ServicePricingDaoImpl.getInstance();

        List<ServicePricing> result = instance.list();

        assertEquals(servicePricings, result);
    }
}
