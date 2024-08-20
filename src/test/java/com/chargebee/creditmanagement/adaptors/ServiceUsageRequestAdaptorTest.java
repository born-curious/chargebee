package com.chargebee.creditmanagement.adaptors;

import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.enums.TransactionType;
import com.chargebee.creditmanagement.models.requests.ServiceUsageRequest;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServiceUsageRequestAdaptorTest {

    @Test
    public void testApply() {
        ServiceUsageRequest request = ServiceUsageRequest.builder()
                .userId("user123")
                .build();

        ServiceUsageRequestAdaptor adaptor = ServiceUsageRequestAdaptor.getInstance();

        // Act
        Transaction transaction = adaptor.apply(request);

        // Assert
        assertNotNull(transaction);
        assertNotNull(transaction.getTxnId());
        assertEquals("user123", transaction.getUserId());
        assertEquals(TransactionType.USAGE, transaction.getTransactionType());
        assertNotNull(transaction.getCreationTimestamp());
        assertTrue(Math.abs(new Date().getTime() - transaction.getCreationTimestamp().getTime()) < 1000);
    }
}
