package com.app.InsightCore_AI.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.InsightCore_AI.dto.SaleRequestDTO;
import com.app.InsightCore_AI.dto.SaleResponseDTO;
import com.app.InsightCore_AI.dto.ChartData;
import com.app.InsightCore_AI.dto.SalesStatsDTO;
import com.app.InsightCore_AI.services.SaleService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public List<SaleResponseDTO> getAllSales() {
        return saleService.getAllSales();
    }

    @PostMapping
    public SaleResponseDTO createSale(
            @RequestBody SaleRequestDTO saleRequestDTO) {
        return saleService.createSale(saleRequestDTO);
    }

    @GetMapping("/daily")
    public List<Map<String, Object>> getDailySales() {
        return saleService.getDailySales();
    }

    @GetMapping("/monthly")
    public List<Map<String, Object>> getMonthlySales() {
        return saleService.getMonthlySales();
    }

    @GetMapping("/chart/monthly")
    public ChartData getMonthlyChartData() {
        return saleService.getMonthlyChartData();
    }

    @GetMapping("/chart/products")
    public ChartData getTopProductsChartData() {
        return saleService.getTopProductsChartData();
    }

    @GetMapping("/stats")
    public SalesStatsDTO getSalesStats() {
        return saleService.getSalesStats();
    }

}
