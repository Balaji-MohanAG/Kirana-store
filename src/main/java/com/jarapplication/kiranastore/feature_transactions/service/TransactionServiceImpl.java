package com.jarapplication.kiranastore.feature_transactions.service;

import com.jarapplication.kiranastore.feature_transactions.dao.TransactionDao;
import com.jarapplication.kiranastore.feature_transactions.enums.TransactionType;
import com.jarapplication.kiranastore.feature_transactions.model.PurchaseRequest;
import com.jarapplication.kiranastore.feature_transactions.model.TransactionDto;
import com.jarapplication.kiranastore.feature_transactions.entity.TransactionEntity;
import com.jarapplication.kiranastore.feature_transactions.model.PurchaseResponse;
import com.jarapplication.kiranastore.feature_transactions.util.TransactionDtoUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    private final BillingServiceImp billingService;
    private final TransactionDao transactionDao;

    @Autowired
    public TransactionServiceImpl(BillingServiceImp billingService,
                                  TransactionDao transactionDao) {
        this.billingService = billingService;
        this.transactionDao = transactionDao;
    }

    @Transactional
    @Override
    public PurchaseResponse makeRefund(String billId, String userId) {
        if(billId==null||userId==null){
            throw new IllegalArgumentException("billId and userId cannot be null");
        }
        List<TransactionEntity> transactions = transactionDao.findByBillId(billId);
        if (transactions.isEmpty()) {
            throw new RuntimeException("transaction not found");
        }

        for (TransactionEntity transactionEntity : transactions) {
            if(!transactionEntity.getUserId().equals(userId)){
                throw new RuntimeException("Transaction refund failed");
            }
            if(transactionEntity.getTransactionType().equals(TransactionType.REFUND)){
                throw new RuntimeException("Refund transaction already exist");
            }
        }
        double amount = transactions.getFirst().getAmount();

        transactionDao.save(TransactionDtoUtil.toTransactionEntity(billId, userId, amount));
        return null;
    }

    @Transactional
    @Override
    public PurchaseResponse makePurchase(PurchaseRequest purchaseRequest) {
        if(purchaseRequest==null){
            throw new IllegalArgumentException("purchaseRequest cannot be null");
        }
        TransactionDto transactionDto = billingService.generateBills(purchaseRequest);
        transactionDao.save(TransactionDtoUtil.TransactionEntityDTO(transactionDto, TransactionType.PURCHASE));

        return transactionResponseDto(transactionDto);

    }
    public PurchaseResponse transactionResponseDto(TransactionDto transactionDto) {
        PurchaseResponse purchaseResponseDto = new PurchaseResponse();
        purchaseResponseDto.setBillId(transactionDto.getBillId());
        purchaseResponseDto.setAmount(transactionDto.getAmount());
        purchaseResponseDto.setBillItems(transactionDto.getBillItems());
        purchaseResponseDto.setTransactionType(transactionDto.getTransactionType());
        return purchaseResponseDto;
    }



}
