package com.jarapplication.kiranastore.feature_reports.dao;

import com.jarapplication.kiranastore.feature_transactions.repository.TransactionRepository;
import com.jarapplication.kiranastore.feature_transactions.entity.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

import static com.jarapplication.kiranastore.feature_reports.util.DateUtil.*;

@Component
public class ReportDao {
    private TransactionRepository transactionRepository;

    @Autowired
    public ReportDao(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Retrieve Transactions for a week
     * @param weekNumber
     * @param month
     * @param year
     * @return
     */
    public List<TransactionEntity> getTransactionsForWeek(int weekNumber, int month, int year) {
        return transactionRepository.findTransactionsByDateRange(getStartOfWeek(weekNumber, month, year), getEndOfWeek(weekNumber, month, year));
    }

    /**
     * Retrieve Transactions for a month
     * @param month
     * @param year
     * @return
     */
    public List<TransactionEntity> getTransactionsForMonth(int month, int year) {
        return transactionRepository.findTransactionsByDateRange(
                getStartOfMonth(month, year), getEndOfMonth(month, year));
    }


}