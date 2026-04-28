package com.app.InsightCore_AI.repository;

import com.app.InsightCore_AI.model.Sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    // ================= 
    // BASIC (KEEP FOR DEBUG / SIMPLE USE)  
    // =================

    List<Sale> findBySaleDateBetween(LocalDate startDate, LocalDate endDate);

    List<Sale> findBySaleDateBetweenAndProductNameContainingIgnoreCase(
            LocalDate startDate,
            LocalDate endDate,
            String productName
    );

    // ================= 
    // AGGREGATION (PRODUCTION) 
    // =================

    // 1️⃣ Top Products
    @Query("""
    SELECT s.productName, SUM(s.amount)
    FROM Sale s
    WHERE s.saleDate BETWEEN :start AND :end
    GROUP BY s.productName
    ORDER BY SUM(s.amount) DESC
    """)
    List<Object[]> findTopProducts(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    // 2️⃣ Sales by Month (PostgreSQL)
    @Query("""
    SELECT FUNCTION('DATE_TRUNC', 'month', s.saleDate), SUM(s.amount)
    FROM Sale s
    WHERE s.saleDate BETWEEN :start AND :end
    GROUP BY FUNCTION('DATE_TRUNC', 'month', s.saleDate)
    ORDER BY 1
    """)
    List<Object[]> findSalesByMonth(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    // 3️⃣ Sales by Month + Product filter
    @Query("""
    SELECT FUNCTION('DATE_TRUNC', 'month', s.saleDate), SUM(s.amount)
    FROM Sale s
    WHERE s.saleDate BETWEEN :start AND :end
    AND LOWER(s.productName) LIKE LOWER(CONCAT('%', :product, '%'))
    GROUP BY FUNCTION('DATE_TRUNC', 'month', s.saleDate)
    ORDER BY 1
    """)
    List<Object[]> findSalesByMonthAndProduct(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("product") String product
    );

    // 4️⃣ Total Amount
    @Query("""
    SELECT COALESCE(SUM(s.amount), 0)
    FROM Sale s
    WHERE s.saleDate BETWEEN :start AND :end
    """)
    double sumSales(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    // 5️⃣ Count Sales
    @Query("""
    SELECT COUNT(s)
    FROM Sale s
    WHERE s.saleDate BETWEEN :start AND :end
    """)
    long countSales(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}