package com.app.InsightCore_AI.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaleRequestDTO {
    private String productName;
    private Double amount;
    private LocalDate saleDate;
}
