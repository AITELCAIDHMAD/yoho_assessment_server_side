package com.yoho.web;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yoho.entities.OEEModel;
import com.yoho.entities.StatUsesModel;
import com.yoho.entities.TemperatureModel;
import com.yoho.service.ReportService;

@RestController
public class ReportController {
	private static final Logger logger = LogManager.getLogger(ReportController.class);

	@Autowired
	private ReportService reportService;

	@RequestMapping(value = "/api/report/data1/{date}", method = RequestMethod.GET)
	@ResponseBody
	public List<StatUsesModel> getReport1(@PathVariable("date") String date) {
		logger.info("getReport1 ");
			return  reportService.getStatusUsesReport(date);
	}
	
	@RequestMapping(value = "/api/report/data2/{date}", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, String> getReport2(@PathVariable("date") String date) {
		logger.info("getReport2 ");
             return reportService.getTemperatureReport(date);
	}
	
	
	
	@RequestMapping(value = "/api/report/data3/{date}", method = RequestMethod.GET)
	@ResponseBody
	public List<OEEModel> getReport3(@PathVariable("date") String date) {
		logger.info("getReport3 ");
		return reportService.getOEEReport(date);

	}
	
	
	
}
