package com.yoho.entities;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatUsesModel {
	private String machine;
	private String datetimeFrom;
	private String datetimeTo;
	private Double scrapPercentage;
	private Double downtimePercentage;
	private Double production;
	private Map<Integer, Double> listNetproductionPerHoure;
}
