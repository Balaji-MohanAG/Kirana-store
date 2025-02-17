package com.jarapplication.kiranastore.kakfa;

import com.jarapplication.kiranastore.feature_reports.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ReportKafkaListener {

    ReportService reportService;

    @Autowired

    public ReportKafkaListener(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Kafka Consumer which triggers to generate reports
     * @param message
     */
    @KafkaListener(topics = "test-topic")
    public void processUserAttributionEvent(String message) {

        if(message.equals("Weekly Report")){
            System.out.println(reportService.getWeeklyReport());
        }
        if(message.equals("Monthly Report")){
            System.out.println(reportService.getWeeklyReport());
        }
        if(message.equals("Yearly Report")){
            System.out.println(reportService.getWeeklyReport());
        }

    }
}
