package com.app.InsightCore_AI.Mapper;

import com.app.InsightCore_AI.dto.SaleRequestDTO;
import com.app.InsightCore_AI.dto.SaleResponseDTO;
import com.app.InsightCore_AI.model.Sale;

public class SaleMapper {
    public static SaleResponseDTO toDTO(Sale sale) {
        return new SaleResponseDTO(
                sale.getId(),
                sale.getProductName(),
                sale.getAmount(),
                sale.getSaleDate()
        );
    }
    
    public static Sale toEntity(SaleRequestDTO saleDTO) {
        Sale sale = new Sale();
        sale.setProductName(saleDTO.getProductName());
        sale.setAmount(saleDTO.getAmount());
        sale.setSaleDate(saleDTO.getSaleDate());
        return sale;
    }
}
