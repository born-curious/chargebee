package com.chargebee.creditmanagement.adaptors;

import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.enums.TransactionType;
import com.chargebee.creditmanagement.models.requests.PurchaseCreditPackageRequest;
import com.chargebee.creditmanagement.utils.IdGeneratorUtil;

import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

public class PurchaseCreditPackageRequestAdaptor implements Function<PurchaseCreditPackageRequest, Transaction> {

    private static PurchaseCreditPackageRequestAdaptor instance = null;

    @Override
    public Transaction apply(PurchaseCreditPackageRequest purchaseCreditPackageRequest) {
        return Transaction.builder()
                .txnId(IdGeneratorUtil.generateTxnId())
                .userId(purchaseCreditPackageRequest.getUserId())
                .transactionType(TransactionType.PURCHASE)
                .creationTimestamp(new Date())
                .build();
    }

    public static PurchaseCreditPackageRequestAdaptor getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (PurchaseCreditPackageRequestAdaptor.class) {
                if (Objects.isNull(instance)) {
                    instance = new PurchaseCreditPackageRequestAdaptor();
                }
            }
        }
        return instance;
    }

    private PurchaseCreditPackageRequestAdaptor() {
    }
}
