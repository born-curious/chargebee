package com.chargebee.creditmanagement.utils;

import java.util.UUID;

public final class IdGeneratorUtil {

    public static String generateTxnId() {
        return "TXN_" + UUID.randomUUID();
    }

    private IdGeneratorUtil() {
    }
}
