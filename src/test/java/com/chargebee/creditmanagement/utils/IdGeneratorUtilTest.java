package com.chargebee.creditmanagement.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IdGeneratorUtilTest {

    @Test
    void testGenerateTxnId() {
        String txnId = IdGeneratorUtil.generateTxnId();
        assertNotNull(txnId);
        assertTrue(txnId.startsWith("TXN_"));
        assertTrue(txnId.length() > "TXN_".length());
    }
}
