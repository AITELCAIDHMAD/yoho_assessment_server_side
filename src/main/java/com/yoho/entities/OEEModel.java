package com.yoho.entities;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OEEModel {
	private String machine;
	private String datetimeFrom;
	private String  datetimeTo;
	private Double  performance;
	private Double availability;
	private Double quality;
	private Double oee;
}
