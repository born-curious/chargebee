package com.chargebee.creditmanagement.adaptors;

import com.chargebee.creditmanagement.models.data.CreditPackage;
import com.chargebee.creditmanagement.models.enums.CreditPackageType;
import com.chargebee.creditmanagement.models.requests.CreditPackageRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreditPackageRequestAdaptorTest {

    @Test
    public void testApply() {
        CreditPackageRequest request = CreditPackageRequest.builder()
                .creditPackageType(CreditPackageType.PREMIUM)
                .credits(BigDecimal.valueOf(10))
                .cost(BigDecimal.valueOf(100.0))
                .build();

        CreditPackageRequestAdaptor adaptor = CreditPackageRequestAdaptor.getInstance();

        CreditPackage creditPackage = adaptor.apply(request);

        assertNotNull(creditPackage);
        assertEquals(CreditPackageType.PREMIUM, creditPackage.getCreditPackageType());
        assertEquals(BigDecimal.valueOf(10), creditPackage.getCredits());
        assertEquals(BigDecimal.valueOf(100.0), creditPackage.getCost());
    }
}
