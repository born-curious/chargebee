package com.chargebee.creditmanagement.daos.impl;

import com.chargebee.creditmanagement.datastores.impl.CreditPackageDatastore;
import com.chargebee.creditmanagement.models.data.CreditPackage;
import com.chargebee.creditmanagement.models.enums.CreditPackageType;
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
public class CreditPackageDaoImplTest {

    private static CreditPackageDatastore creditPackageDatastoreMock;
    private static MockedStatic<CreditPackageDatastore> creditPackageDatastoreMockedStatic;

    @BeforeAll
    public static void setup() {
        creditPackageDatastoreMock = mock(CreditPackageDatastore.class);
        creditPackageDatastoreMockedStatic = mockStatic(CreditPackageDatastore.class);
        creditPackageDatastoreMockedStatic.when(CreditPackageDatastore::getInstance)
                .thenReturn(creditPackageDatastoreMock);
    }

    @AfterAll
    public static void tearDown() {
        creditPackageDatastoreMockedStatic.close();
    }

    @Test
    void testInsertCreditPackage() {
        CreditPackage creditPackage = mock(CreditPackage.class);
        when(creditPackage.getCreditPackageType()).thenReturn(CreditPackageType.PREMIUM);

        CreditPackageDaoImpl instance = CreditPackageDaoImpl.getInstance();

        instance.insertCreditPackage(creditPackage);

        verify(creditPackageDatastoreMock).put(CreditPackageType.PREMIUM, creditPackage);
    }

    @Test
    void testUpdateCreditPackage() {
        CreditPackage creditPackage = mock(CreditPackage.class);
        when(creditPackage.getCreditPackageType()).thenReturn(CreditPackageType.PREMIUM);

        CreditPackageDaoImpl instance = CreditPackageDaoImpl.getInstance();

        instance.updateCreditPackage(creditPackage);

        verify(creditPackageDatastoreMock).put(CreditPackageType.PREMIUM, creditPackage);
    }

    @Test
    void testGetCreditPackage() {
        CreditPackage creditPackage = mock(CreditPackage.class);
        when(creditPackageDatastoreMock.get(CreditPackageType.PREMIUM)).thenReturn(creditPackage);

        CreditPackageDaoImpl instance = CreditPackageDaoImpl.getInstance();

        CreditPackage result = instance.getCreditPackage(CreditPackageType.PREMIUM);

        assertEquals(creditPackage, result);
    }

    @Test
    void testGetCreditPackage_NotFound() {
        CreditPackageDaoImpl instance = CreditPackageDaoImpl.getInstance();

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> instance.getCreditPackage(CreditPackageType.PREMIUM));
        assertEquals("Credit Package Not Found for CreditPackageType - PREMIUM", exception.getMessage());
    }

    @Test
    void testList() {
        List<CreditPackage> creditPackages = new ArrayList<>();
        creditPackages.add(mock(CreditPackage.class));
        creditPackages.add(mock(CreditPackage.class));

        when(creditPackageDatastoreMock.values()).thenReturn(creditPackages);

        CreditPackageDaoImpl instance = CreditPackageDaoImpl.getInstance();

        List<CreditPackage> result = instance.list();

        assertEquals(creditPackages, result);
    }
}