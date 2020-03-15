package com.yoho.entities;

import java.util.Date;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TemperatureModel {
	private HashMap<String, String> listTemperature;
}
