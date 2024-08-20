package com.chargebee.creditmanagement.daos.impl;

import com.chargebee.creditmanagement.daos.CreditPackageDao;
import com.chargebee.creditmanagement.datastores.impl.CreditPackageDatastore;
import com.chargebee.creditmanagement.models.data.CreditPackage;
import com.chargebee.creditmanagement.models.enums.CreditPackageType;
import com.chargebee.creditmanagement.models.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreditPackageDaoImpl implements CreditPackageDao {

    private static CreditPackageDaoImpl instance = null;
    private final CreditPackageDatastore creditPackageDatastore;

    @Override
    public void insertCreditPackage(CreditPackage creditPackage) {
        creditPackageDatastore.put(creditPackage.getCreditPackageType(), creditPackage);
    }

    @Override
    public void updateCreditPackage(CreditPackage creditPackage) {
        creditPackageDatastore.put(creditPackage.getCreditPackageType(), creditPackage);
    }

    @Override
    public CreditPackage getCreditPackage(CreditPackageType creditPackageType) {
        CreditPackage creditPackage = creditPackageDatastore.get(creditPackageType);
        if (Objects.isNull(creditPackage)) {
            throw new NotFoundException("Credit Package Not Found for CreditPackageType - " + creditPackageType);
        }
        return creditPackage;
    }

    @Override
    public List<CreditPackage> list() {
        return new ArrayList<>(creditPackageDatastore.values());
    }

    public static CreditPackageDaoImpl getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (CreditPackageDaoImpl.class) {
                if (Objects.isNull(instance)) {
                    instance = new CreditPackageDaoImpl();
                }
            }
        }
        return instance;
    }

    private CreditPackageDaoImpl() {
        this.creditPackageDatastore = CreditPackageDatastore.getInstance();
    }
}
