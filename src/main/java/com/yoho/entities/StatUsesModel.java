package com.yoho.entities;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatUsesModel {
	private  String MACHINE;
	private  String DATETIME_FROM;
	private  String DATETIME_TO;
	private  Double SCRAP_PERCENTAGE;
	private  Double DOWNTIME_PERCENTAGE;
	private  Double PRODUCTION;
	private  Map<Integer,Double> listNetproductionPerHoure;
}
