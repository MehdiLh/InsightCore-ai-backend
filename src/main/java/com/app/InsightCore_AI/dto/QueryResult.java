package com.app.InsightCore_AI.dto;

public record QueryResult(
        ChartData chartData,
        int rows,
        double total
) {}
