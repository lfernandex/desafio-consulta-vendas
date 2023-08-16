package com.devsuperior.dsmeta.projection;

import java.time.LocalDate;

public interface ReportProjection {

    Long getId();
    LocalDate getDate();
    Double getTotal_sales();
    String getName();
     
}
