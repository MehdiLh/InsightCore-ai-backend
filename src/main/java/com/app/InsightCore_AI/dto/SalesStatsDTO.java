package com.app.InsightCore_AI.dto;

import lombok.Getter;

@Getter
public class SalesStatsDTO {
    private long totalOrders;
    private double totalSales;
    private double totalRevenue;
    private double estimatedProfit;

    public SalesStatsDTO() {
    }

    public SalesStatsDTO(long totalOrders, double totalSales, double totalRevenue, double estimatedProfit) {
        this.totalOrders = totalOrders;
        this.totalSales = totalSales;
        this.totalRevenue = totalRevenue;
        this.estimatedProfit = estimatedProfit;
    }
}
