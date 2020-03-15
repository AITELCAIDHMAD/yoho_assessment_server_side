package com.yoho.web;

import java.util.HashMap;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yoho.entities.OEEModel;
import com.yoho.entities.StatUsesModel;
import com.yoho.entities.TemperatureModel;
import com.yoho.service.ReportService;

@Controller
@RequestMapping("/")
public class WebController {

	@Autowired
	private ReportService reportService;

	// This For MVC Server Side with theamleaf
	@RequestMapping(value = "/index/{date}")
	public String indexPage(Model model, @PathVariable("date") String date) {

		HashMap<String, String> temperatureModel = reportService.getTemperatureReport(date);
		List<OEEModel> OEEModel = reportService.getOEEReport(date);
		List<StatUsesModel> statUsesModel = reportService.getStatusUsesReport(date);

		model.addAttribute("temperatureModel", temperatureModel);
		model.addAttribute("OEEModel", OEEModel);
		model.addAttribute("statUsesModel", statUsesModel);

		return "index";
	}

	@RequestMapping(value = "/")
	public String redirecToIndex() {
		return "redirect:index";
	}
}
