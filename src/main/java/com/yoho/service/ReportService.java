package com.yoho.service;

import java.util.HashMap;
import java.util.List;

import com.yoho.entities.OEEModel;
import com.yoho.entities.StatUsesModel;
import com.yoho.entities.TemperatureModel;

public interface ReportService {

	HashMap<String, String> getTemperatureReport(String date);
	List<OEEModel> getOEEReport(String date);
	List<StatUsesModel> getStatusUsesReport(String date);
}
