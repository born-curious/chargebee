package com.chargebee.creditmanagement.adaptors;

import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.enums.TransactionType;
import com.chargebee.creditmanagement.models.requests.ServiceUsageRequest;
import com.chargebee.creditmanagement.utils.IdGeneratorUtil;

import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

public class ServiceUsageRequestAdaptor implements Function<ServiceUsageRequest, Transaction> {

    private static ServiceUsageRequestAdaptor instance = null;

    @Override
    public Transaction apply(ServiceUsageRequest serviceUsageRequest) {
        return Transaction.builder()
                .txnId(IdGeneratorUtil.generateTxnId())
                .userId(serviceUsageRequest.getUserId())
                .transactionType(TransactionType.USAGE)
                .creationTimestamp(new Date())
                .build();
    }

    public static ServiceUsageRequestAdaptor getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (ServiceUsageRequestAdaptor.class) {
                if (Objects.isNull(instance)) {
                    instance = new ServiceUsageRequestAdaptor();
                }
            }
        }
        return instance;
    }

    private ServiceUsageRequestAdaptor() {
    }
}
