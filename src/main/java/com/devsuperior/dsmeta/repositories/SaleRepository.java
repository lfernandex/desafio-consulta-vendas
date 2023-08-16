package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projection.ReportProjection;
import com.devsuperior.dsmeta.projection.SummyProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    
    @Query(nativeQuery = true, value = "SELECT tb_sales.amount, tb_seller.name "
        + "FROM tb_sales "
        + "INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id "
        + "WHERE (:minDate IS NULL OR tb_sales.date >= :minDate) "
        + "AND (:maxDate IS NULL OR tb_sales.date <= :maxDate) "
        + "ORDER BY tb_sales.date DESC",
        
        countQuery = "SELECT COUNT(*) FROM tb_sales INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id "
        + "WHERE (:minDate IS NULL OR tb_sales.date >= :minDate) "
        + "AND (:maxDate IS NULL OR tb_sales.date <= :maxDate)")
    Page<SummyProjection> getSumery(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, Pageable pageable);


    @Query(nativeQuery = true, value = "SELECT tb_sales.id, tb_sales.date, SUM(tb_sales.amount) as total_sales, tb_seller.name "
        + "FROM tb_sales "
        + "INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id "
        + "WHERE (:minDate IS NULL OR tb_sales.date >= :minDate) "
        + "AND (:maxDate IS NULL OR tb_sales.date <= :maxDate) "
        + "AND (:name IS NULL OR UPPER(tb_seller.name) LIKE UPPER(CONCAT('%', :name, '%'))) "
        + "GROUP BY tb_sales.id, tb_sales.date, tb_seller.name "
        + "ORDER BY tb_sales.date DESC",

        countQuery = "SELECT COUNT(*) FROM tb_sales "
        + "INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id "
        + "WHERE (:minDate IS NULL OR tb_sales.date >= :minDate) "
        + "AND (:maxDate IS NULL OR tb_sales.date <= :maxDate) "
        + "AND (:name IS NULL OR UPPER(tb_seller.name) LIKE UPPER(CONCAT('%', :name, '%')))")
    Page<ReportProjection> getReport(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, String name, Pageable pageable);
}
