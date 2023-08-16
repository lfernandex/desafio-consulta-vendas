package com.devsuperior.dsmeta;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;

import com.devsuperior.dsmeta.projection.ReportProjection;
import com.devsuperior.dsmeta.projection.SummyProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@SpringBootApplication
public class DsmetaApplication implements CommandLineRunner{

	@Autowired
	private SaleRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(DsmetaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Page<SummyProjection> list = repository.getSumery(LocalDate.parse("2022-01-01"), LocalDate.parse("2022-06-30"), null);

		for (SummyProjection r : list){
			System.out.println("Seller Name: " + r.getName());
			System.out.println("Total Amount: " + r.getAmount());
			System.out.println("----------------------");
		}

		Page<ReportProjection> list2 = repository.getReport(LocalDate.parse("2022-05-01"), LocalDate.parse("2022-05-31"), "odinson", null);

		for (ReportProjection r : list2){
			System.out.println("Id: " + r.getId());
			System.out.println("Date: " + r.getDate());
			System.out.println("Amount: " + r.getTotal_sales());
			System.out.println("Seller Name: " + r.getName());
			System.out.println("----------------------");
		}
	}
}
