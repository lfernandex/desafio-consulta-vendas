package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projection.ReportProjection;
import com.devsuperior.dsmeta.projection.SummyProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}


	public Page<SaleReportDTO> getReport(String minDate, String maxDate, String name, Pageable pageable){
		LocalDate maxDat;
		LocalDate minDat;
		

		if (maxDate == null || maxDate.isEmpty()) {
			maxDat = LocalDate.now();
		} else {
			maxDat = LocalDate.parse(maxDate);
		}
		
		if (minDate == null || minDate.isEmpty()) {
			minDat = maxDat.minusYears(1L);
		} else {
			minDat = LocalDate.parse(minDate);
		}

		Page<ReportProjection> report = repository.getReport(minDat, maxDat, name, pageable);

		return report.map(x -> new SaleReportDTO(x));
	}

	public Page<SaleSummaryDTO> getSumery(String minDate, String maxDate, Pageable pageable){

		LocalDate maxDat;
		LocalDate minDat;
		

		if (maxDate == null || maxDate.isEmpty()) {
			maxDat = LocalDate.now();
		} else {
			maxDat = LocalDate.parse(maxDate);
		}
		
		if (minDate == null || minDate.isEmpty()) {
			minDat = maxDat.minusYears(1L);
		} else {
			minDat = LocalDate.parse(minDate);
		}

		Page<SummyProjection> result = repository.getSumery(minDat, maxDat, pageable);

		return result.map(x -> new SaleSummaryDTO(x));
	}
}
