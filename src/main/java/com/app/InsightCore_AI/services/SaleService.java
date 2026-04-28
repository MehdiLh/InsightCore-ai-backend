package com.app.InsightCore_AI.services;

import org.springframework.stereotype.Service;

import com.app.InsightCore_AI.dto.SaleRequestDTO;
import com.app.InsightCore_AI.dto.SaleResponseDTO;
import com.app.InsightCore_AI.dto.ChartData;
import com.app.InsightCore_AI.dto.SalesStatsDTO;
import com.app.InsightCore_AI.model.Sale;
import com.app.InsightCore_AI.repository.SaleRepository;
import com.app.InsightCore_AI.Mapper.SaleMapper;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    // GET ALL SALES
    public List<SaleResponseDTO> getAllSales() {
        return saleRepository.findAll().stream().map(SaleMapper::toDTO).collect(Collectors.toList());
    }

    // POST SALES
    public SaleResponseDTO createSale(SaleRequestDTO saleRequestDTO) {
        Sale sale = SaleMapper.toEntity(saleRequestDTO);
        Sale savedSale = saleRepository.save(sale);
        return SaleMapper.toDTO(savedSale);
    }

    // GET DAILY SALES AGGREGATION
    public List<Map<String, Object>> getDailySales() {
        List<Object[]> results = saleRepository.findSalesByMonth(
            LocalDate.now().minusMonths(1),
            LocalDate.now()
        );

        return results.stream()
            .map(row -> {
                Map<String, Object> map = new HashMap<>();
                map.put("date", row[0].toString());
                map.put("amount", ((Number) row[1]).doubleValue());
                return map;
            })
            .collect(Collectors.toList());
    }

    // GET MONTHLY SALES AGGREGATION
    public List<Map<String, Object>> getMonthlySales() {
        List<Object[]> results = saleRepository.findSalesByMonth(
            LocalDate.now().minusMonths(6),
            LocalDate.now()
        );

        return results.stream()
            .map(row -> {
                Map<String, Object> map = new HashMap<>();
                map.put("date", row[0].toString());
                map.put("amount", ((Number) row[1]).doubleValue());
                return map;
            })
            .collect(Collectors.toList());
    }

    // GET MONTHLY CHART DATA
    public ChartData getMonthlyChartData() {
        List<Object[]> results = saleRepository.findSalesByMonth(
            LocalDate.now().minusMonths(6),
            LocalDate.now()
        );

        List<String> labels = results.stream()
            .map(row -> row[0].toString())
            .collect(Collectors.toList());

        List<Double> values = results.stream()
            .map(row -> ((Number) row[1]).doubleValue())
            .collect(Collectors.toList());

        return new ChartData(labels, values);
    }

    // GET TOP PRODUCTS CHART DATA
    public ChartData getTopProductsChartData() {
        List<Object[]> results = saleRepository.findTopProducts(
            LocalDate.now().minusMonths(3),
            LocalDate.now()
        );

        List<String> labels = results.stream()
            .map(row -> (String) row[0])
            .collect(Collectors.toList());

        List<Double> values = results.stream()
            .map(row -> ((Number) row[1]).doubleValue())
            .collect(Collectors.toList());

        return new ChartData(labels, values);
    }

    // GET SALES STATS
    public SalesStatsDTO getSalesStats() {
        LocalDate now = LocalDate.now();
        LocalDate start = now.minusMonths(1);

        long totalOrders = saleRepository.countSales(start, now);
        double totalSales = saleRepository.sumSales(start, now);
        double totalRevenue = totalSales;
        double estimatedProfit = totalRevenue * 0.28;

        return new SalesStatsDTO(totalOrders, totalSales, totalRevenue, estimatedProfit);
    }
}
