package com.app.InsightCore_AI.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.app.InsightCore_AI.dto.ChartData;
import com.app.InsightCore_AI.model.Sale;
import com.app.InsightCore_AI.repository.SaleRepository;

import org.springframework.stereotype.Service;

@Service
public class ChartService {

    private final SaleRepository saleRepository;

    public ChartService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }
    public ChartData generateChartData(Long userId, LocalDate start, LocalDate end) {
        // fetch sales
        List<Sale> sales = saleRepository.findBySaleDateBetween(start, end);
        if (sales == null) sales = new ArrayList<>(); // safety net

        List<String> labels = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        for (Sale sale : sales) {
            // skip if data is null
            if (sale.getProductName() != null && sale.getAmount() != null) {
                labels.add(sale.getProductName());
                values.add(sale.getAmount());
            }
        }

        // make sure lists are never null
        return new ChartData(
            labels != null ? labels : new ArrayList<>(),
            values != null ? values : new ArrayList<>()
        );
    }
}
