package com.jarapplication.kiranastore.feature_reports.dao;

import static com.jarapplication.kiranastore.feature_reports.util.DateUtil.*;

import com.jarapplication.kiranastore.feature_transactions.entity.TransactionEntity;
import com.jarapplication.kiranastore.feature_transactions.repository.TransactionRepository;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// repo
@Component
public class ReportDao {
    private TransactionRepository transactionRepository;

    @Autowired
    public ReportDao(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Retrieve Transactions for a week
     *
     * @param weekNumber
     * @param month
     * @param year
     * @return
     */
    public List<TransactionEntity> getTransactionsForWeek(int weekNumber, int month, int year) {
        Date startOfWeek = getStartOfWeek(weekNumber, month, year);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startOfWeek);
        calendar.add(Calendar.DAY_OF_WEEK, 6); // Get end of the week
        Date endOfWeek = getEndOfDay(calendar.getTime());

        return transactionRepository.findTransactionsByDateRange(startOfWeek, endOfWeek);
    }

    /**
     * Retrieve Transactions for a month
     *
     * @param month
     * @param year
     * @return
     */
    public List<TransactionEntity> getTransactionsForMonth(int month, int year) {
        return transactionRepository.findTransactionsByDateRange(
                getStartOfMonth(month, year), getEndOfMonth(month, year));
    }
}
