package com.chargebee.creditmanagement.adaptors;

import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.enums.TransactionType;
import com.chargebee.creditmanagement.models.requests.PurchaseCreditPackageRequest;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PurchaseCreditPackageRequestAdaptorTest {

    @Test
    public void testApply() {
        PurchaseCreditPackageRequest request = PurchaseCreditPackageRequest.builder()
                .userId("user123")
                .build();

        PurchaseCreditPackageRequestAdaptor adaptor = PurchaseCreditPackageRequestAdaptor.getInstance();

        Transaction transaction = adaptor.apply(request);

        assertNotNull(transaction);
        assertNotNull(transaction.getTxnId());
        assertEquals("user123", transaction.getUserId());
        assertEquals(TransactionType.PURCHASE, transaction.getTransactionType());
        assertNotNull(transaction.getCreationTimestamp());
        assertTrue(Math.abs(new Date().getTime() - transaction.getCreationTimestamp().getTime()) < 1000);
    }
}
