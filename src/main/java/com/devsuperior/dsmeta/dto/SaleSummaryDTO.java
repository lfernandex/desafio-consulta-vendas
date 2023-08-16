package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projection.SummyProjection;

public class SaleSummaryDTO {

    private String name;
    private Double amount;
    
    public SaleSummaryDTO(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }

    public SaleSummaryDTO(SummyProjection entity){
        name = entity.getName();
        amount = entity.getAmount();
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    
    
}
