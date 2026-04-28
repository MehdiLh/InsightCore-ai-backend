package com.app.InsightCore_AI.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.InsightCore_AI.dto.AiQueryPlan;
import com.app.InsightCore_AI.dto.ChartData;
import com.app.InsightCore_AI.dto.QueryResult;
import com.app.InsightCore_AI.repository.SaleRepository;

@Service
public class AiDataService {
    
    private final SaleRepository saleRepository;

    public AiDataService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public QueryResult fetch(AiQueryPlan plan) {

        String intent = Optional.ofNullable(plan.getIntent()).orElse("");

        return switch (intent) {

            case "top_products" -> fetchTopProducts();

            case "product_sales" -> fetchProductSales(plan);

            default -> fetchMonthlySales();
        };
    }

    private QueryResult fetchTopProducts() {

        List<Object[]> rows = saleRepository.findTopProducts(LocalDate.now().minusMonths(3), LocalDate.now());

        return map(rows);
    }

    private QueryResult fetchProductSales(AiQueryPlan plan) {

        if (plan.getProductName() == null) {
            return fetchMonthlySales();
        }

        List<Object[]> rows =
                saleRepository.findSalesByMonthAndProduct(
                        LocalDate.now().minusMonths(3),
                        LocalDate.now(),
                        plan.getProductName()
                );

        return map(rows);
    }

    private QueryResult fetchMonthlySales() {

        List<Object[]> rows =
                saleRepository.findSalesByMonth(
                        LocalDate.now().minusMonths(3),
                        LocalDate.now()
                );

        return map(rows);
    }

    private QueryResult map(List<Object[]> rows) {

        List<String> labels = rows.stream().map(r -> r[0].toString()).toList();

        List<Double> values = rows.stream()
                .map(r -> ((Number) r[1]).doubleValue())
                .toList();

        double total = values.stream().mapToDouble(Double::doubleValue).sum();

        return new QueryResult(
                new ChartData(labels, values),
                rows.size(),
                total
        );
    }

    public String buildFacts(AiQueryPlan plan, QueryResult result) {

        return """
        intent=%s
        metric=%s
        groupBy=%s
        product=%s

        rows=%s
        total=%s
        labels=%s
        values=%s
        """.formatted(
                plan.getIntent(),
                plan.getMetric(),
                plan.getGroupBy(),
                plan.getProductName(),
                result.rows(),
                result.total(),
                result.chartData().getLabels(),
                result.chartData().getValues()
        );
    }
}
