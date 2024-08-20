package com.chargebee.creditmanagement.services.impl;

import com.chargebee.creditmanagement.adaptors.CreditPackageRequestAdaptor;
import com.chargebee.creditmanagement.daos.CreditPackageDao;
import com.chargebee.creditmanagement.daos.impl.CreditPackageDaoImpl;
import com.chargebee.creditmanagement.models.data.CreditPackage;
import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.data.UserCredit;
import com.chargebee.creditmanagement.models.enums.CreditPackageType;
import com.chargebee.creditmanagement.models.enums.TransactionStatus;
import com.chargebee.creditmanagement.models.exceptions.AlreadyExistsException;
import com.chargebee.creditmanagement.models.exceptions.NotFoundException;
import com.chargebee.creditmanagement.models.requests.CreditPackageRequest;
import com.chargebee.creditmanagement.models.requests.PurchaseCreditPackageRequest;
import com.chargebee.creditmanagement.services.CreditPackageService;
import com.chargebee.creditmanagement.services.TransactionService;
import com.chargebee.creditmanagement.services.UserCreditService;
import com.chargebee.creditmanagement.utils.ValidationUtils;

import java.util.List;
import java.util.Objects;

public class CreditPackageServiceImpl implements CreditPackageService {

    private static CreditPackageServiceImpl instance = null;
    private final CreditPackageDao creditPackageDao;
    private final UserCreditService userCreditService;
    private final TransactionService transactionService;
    private final CreditPackageRequestAdaptor creditPackageRequestAdaptor;

    @Override
    public CreditPackage createCreditPackage(CreditPackageRequest creditPackageRequest) {
        ValidationUtils.validateCreditPackageRequest(creditPackageRequest);
        try {
            creditPackageDao.getCreditPackage(creditPackageRequest.getCreditPackageType());
            throw new AlreadyExistsException("Credit Package Already Exists. Please Update the credit package");
        } catch (NotFoundException ignored) {
        }
        CreditPackage creditPackage = creditPackageRequestAdaptor.apply(creditPackageRequest);
        creditPackageDao.insertCreditPackage(creditPackage);
        return creditPackage;
    }

    @Override
    public CreditPackage updateCreditPackage(CreditPackageRequest creditPackageRequest) {
        ValidationUtils.validateCreditPackageRequest(creditPackageRequest);
        creditPackageDao.getCreditPackage(creditPackageRequest.getCreditPackageType());
        CreditPackage creditPackage = creditPackageRequestAdaptor.apply(creditPackageRequest);
        creditPackageDao.updateCreditPackage(creditPackage);
        return creditPackage;
    }

    @Override
    public CreditPackage getCreditPackage(CreditPackageType creditPackageType) {
        return creditPackageDao.getCreditPackage(creditPackageType);
    }

    @Override
    public List<CreditPackage> viewAllPackages() {
        return creditPackageDao.list();
    }

    @Override
    public Transaction purchaseCredits(PurchaseCreditPackageRequest purchaseCreditPackageRequest) {
        ValidationUtils.validatePurchaseCreditPackageRequest(purchaseCreditPackageRequest);
        CreditPackage creditPackage = creditPackageDao.getCreditPackage(
                purchaseCreditPackageRequest.getCreditPackageType());
        Transaction transaction = transactionService.createTransaction(purchaseCreditPackageRequest,
                creditPackage.getCredits(), TransactionStatus.ACCEPTED);
        UserCredit userCredit = userCreditService.getUserCreditForInsertOrUpdate(
                purchaseCreditPackageRequest.getUserId());
        if (Objects.nonNull(userCredit)) {
            userCreditService.updateUserCredit(userCredit, transaction);
        } else {
            userCreditService.insertUserCredit(transaction);
        }
        return transaction;
    }

    public static CreditPackageServiceImpl getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (CreditPackageServiceImpl.class) {
                if (Objects.isNull(instance)) {
                    instance = new CreditPackageServiceImpl();
                }
            }
        }
        return instance;
    }

    private CreditPackageServiceImpl() {
        this.creditPackageDao = CreditPackageDaoImpl.getInstance();
        this.transactionService = TransactionServiceImpl.getInstance();
        this.userCreditService = UserCreditServiceImpl.getInstance();
        this.creditPackageRequestAdaptor = CreditPackageRequestAdaptor.getInstance();
    }
}
