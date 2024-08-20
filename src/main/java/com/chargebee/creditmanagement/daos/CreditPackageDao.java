package com.chargebee.creditmanagement.daos;

import com.chargebee.creditmanagement.models.data.CreditPackage;
import com.chargebee.creditmanagement.models.enums.CreditPackageType;

import java.util.List;

public interface CreditPackageDao {

    void insertCreditPackage(CreditPackage creditPackage);

    void updateCreditPackage(CreditPackage creditPackage);

    CreditPackage getCreditPackage(CreditPackageType creditPackageType);

    List<CreditPackage> list();
}
