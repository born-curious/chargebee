package com.chargebee.creditmanagement.services.impl;

import com.chargebee.creditmanagement.adaptors.CreateServicePricingRequestAdaptor;
import com.chargebee.creditmanagement.adaptors.UpdateServicePricingRequestAdaptor;
import com.chargebee.creditmanagement.daos.ServicePricingDao;
import com.chargebee.creditmanagement.daos.impl.ServicePricingDaoImpl;
import com.chargebee.creditmanagement.models.data.ServicePricing;
import com.chargebee.creditmanagement.models.data.Transaction;
import com.chargebee.creditmanagement.models.data.UserCredit;
import com.chargebee.creditmanagement.models.enums.TransactionStatus;
import com.chargebee.creditmanagement.models.exceptions.NotFoundException;
import com.chargebee.creditmanagement.models.requests.CreateServicePricingRequest;
import com.chargebee.creditmanagement.models.requests.ServiceUsageRequest;
import com.chargebee.creditmanagement.models.requests.UpdateServicePricingRequest;
import com.chargebee.creditmanagement.services.ServicePricingService;
import com.chargebee.creditmanagement.services.TransactionService;
import com.chargebee.creditmanagement.services.UserCreditService;
import com.chargebee.creditmanagement.utils.ValidationUtils;

import java.util.List;
import java.util.Objects;

public class ServicePricingServiceImpl implements ServicePricingService {

    private static ServicePricingServiceImpl instance = null;
    private final ServicePricingDao servicePricingDao;
    private final TransactionService transactionService;
    private final UserCreditService userCreditService;
    private final CreateServicePricingRequestAdaptor createServicePricingRequestAdaptor;
    private final UpdateServicePricingRequestAdaptor updateServicePricingRequestAdaptor;

    @Override
    public ServicePricing createServicePricing(CreateServicePricingRequest createServicePricingRequest) {
        ValidationUtils.validateServicePricingRequest(createServicePricingRequest);
        ServicePricing servicePricing = createServicePricingRequestAdaptor.apply(createServicePricingRequest);
        servicePricingDao.insertServicePricing(servicePricing);
        return servicePricing;
    }

    @Override
    public ServicePricing updateServicePricing(UpdateServicePricingRequest updateServicePricingRequest) {
        ValidationUtils.validateServicePricingRequest(updateServicePricingRequest);
        servicePricingDao.getServicePricing(updateServicePricingRequest.getServiceId());
        ServicePricing servicePricing = updateServicePricingRequestAdaptor.apply(updateServicePricingRequest);
        servicePricingDao.insertServicePricing(servicePricing);
        return servicePricing;
    }

    @Override
    public ServicePricing getServicePricing(String serviceId) {
        return servicePricingDao.getServicePricing(serviceId);
    }

    @Override
    public List<ServicePricing> viewAllServicePricing() {
        return servicePricingDao.list();
    }

    @Override
    public Transaction utilizeService(ServiceUsageRequest serviceUsageRequest) {
        ValidationUtils.validateServiceUsageRequest(serviceUsageRequest);
        ServicePricing servicePricing = servicePricingDao.getServicePricing(serviceUsageRequest.getServiceId());
        try {
            UserCredit userCredit = userCreditService.getUserCreditForUpdate(serviceUsageRequest.getUserId());
            TransactionStatus status = (userCredit.getCreditsAvailable().compareTo(servicePricing.getUsageCost()) < 0)
                    ? TransactionStatus.REJECTED : TransactionStatus.ACCEPTED;
            Transaction transaction = transactionService.createTransaction(serviceUsageRequest,
                    servicePricing.getUsageCost(), status);
            if (TransactionStatus.ACCEPTED == status) {
                userCreditService.updateUserCredit(userCredit, transaction);
            }
            return transaction;
        } catch (NotFoundException nfe) {
            transactionService.createTransaction(serviceUsageRequest, servicePricing.getUsageCost(),
                    TransactionStatus.REJECTED);
            throw nfe;
        }
    }

    public static ServicePricingServiceImpl getInstance() {
        if (Objects.isNull(instance)) {
            synchronized (ServicePricingServiceImpl.class) {
                if (Objects.isNull(instance)) {
                    instance = new ServicePricingServiceImpl();
                }
            }
        }
        return instance;
    }

    private ServicePricingServiceImpl() {
        this.servicePricingDao = ServicePricingDaoImpl.getInstance();
        this.transactionService = TransactionServiceImpl.getInstance();
        this.userCreditService = UserCreditServiceImpl.getInstance();
        this.createServicePricingRequestAdaptor = CreateServicePricingRequestAdaptor.getInstance();
        this.updateServicePricingRequestAdaptor = UpdateServicePricingRequestAdaptor.getInstance();
    }
}
