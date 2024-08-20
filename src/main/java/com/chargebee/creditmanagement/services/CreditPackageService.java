package com.chargebee.creditmanagement.services;

import com.chargebee.creditmanagement.models.data.CreditPackage;
import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.enums.CreditPackageType;
import com.chargebee.creditmanagement.models.requests.CreditPackageRequest;
import com.chargebee.creditmanagement.models.requests.PurchaseCreditPackageRequest;

import java.util.List;

public interface CreditPackageService {

    CreditPackage createCreditPackage(CreditPackageRequest creditPackageRequest);

    CreditPackage updateCreditPackage(CreditPackageRequest creditPackageRequest);

    CreditPackage getCreditPackage(CreditPackageType creditPackageType);

    List<CreditPackage> viewAllPackages();

    Transaction purchaseCredits(PurchaseCreditPackageRequest purchaseCreditPackageRequest);
}
