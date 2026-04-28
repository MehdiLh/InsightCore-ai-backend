package com.app.InsightCore_AI.dto;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class SaleResponseDTO {
    private Long id;
    private String productName;
    private Double amount;
    private LocalDate saleDate;
    
    public SaleResponseDTO() {
    }
    
    public SaleResponseDTO(Long id, String productName, Double amount, LocalDate saleDate) {
        this.id = id;
        this.productName = productName;
        this.amount = amount;
        this.saleDate = saleDate;
    }    

}
