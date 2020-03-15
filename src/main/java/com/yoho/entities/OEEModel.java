package com.yoho.entities;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OEEModel {
	private String MACHINE;
	private String DATETIME_FROM;
	private String  DATETIME_TO;
	private Double  PERFORMANCE;
	private Double AVAILABILITY;
	private Double QUALITY;
	private Double OEE;
}
