package com.chargebee.creditmanagement.adaptors;

import com.chargebee.creditmanagement.models.data.CreditPackage;
import com.chargebee.creditmanagement.models.requests.CreditPackageRequest;

import java.util.Objects;
import java.util.function.Function;

public class CreditPackageRequestAdaptor implements Function<CreditPackageRequest, CreditPackage> {

    private static CreditPackageRequestAdaptor instance = null;

    @Override
    public CreditPackage apply(CreditPackageRequest creditPackageRequest) {
        return CreditPackage.builder()
                .creditPackageType(creditPackageRequest.getCreditPackageType())
                .credits(creditPackageRequest.getCredits())
                .cost(creditPackageRequest.getCost())
                .build();
    }

    public static CreditPackageRequestAdaptor getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (CreditPackageRequestAdaptor.class) {
                if (Objects.isNull(instance)) {
                    instance = new CreditPackageRequestAdaptor();
                }
            }
        }
        return instance;
    }

    private CreditPackageRequestAdaptor() {
    }
}
