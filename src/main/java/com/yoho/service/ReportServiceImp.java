package com.yoho.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoho.dao.ProductionRepository;
import com.yoho.dao.RuntimeRepository;
import com.yoho.entities.OEEModel;
import com.yoho.entities.StatUsesModel;
import com.yoho.entities.TemperatureModel;
import com.yoho.projections.MachineTotalProjection;

@Service
public class ReportServiceImp implements ReportService {
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private RuntimeRepository runtimeRepository;

	@Autowired
	private ProductionRepository productionRepository;

	@Override
	public HashMap<String, String> getTemperatureReport(String date) {
		String inputDate = date + " 00:00:00";
		HashMap<String, List<Double>> listMachineTeperature = new HashMap<String, List<Double>>();

		Date currentDate;

		try {
			currentDate = dateFormat.parse(inputDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate);

			List <String>listMachineName = productionRepository.getListMachine();

			String dateFrom = dateFormat.format(cal.getTime());
			cal.add(Calendar.DAY_OF_WEEK, 1);
			String dateTo = dateFormat.format(cal.getTime());
			System.out.println("dateFrom "+dateFrom);
			
			System.out.println("dateTo "+dateTo);
			
			
			for (String machine : listMachineName) {
				listMachineTeperature.put(machine, new ArrayList<Double>());
				
				
				List<MachineTotalProjection> listTemerature = productionRepository
						.getTemerature(machine,dateFrom, dateTo);

				System.out.println("listAVGTemeratureEvery15Min "+listTemerature);

				for (MachineTotalProjection item : listTemerature) {
					listMachineTeperature.get(machine).add(item.getTotal());
				}
				
				
				
				
			}

		
				

				

			

			return getTemeratureLabelColor(listMachineTeperature);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

		
	}

	private HashMap<String, String> getTemeratureLabelColor(HashMap<String, List<Double>> listMachineTeperature) {

		HashMap<String,String> machineTeperature=new HashMap<String, String>();
		
		for (Map.Entry<String, List<Double>> entry : listMachineTeperature.entrySet()) {

			
			  int counter = 0;
		      String status = "good/green";
		      
			for(Double item :entry.getValue()) {
				if (item> 100) {
	            	status = "fatal/red";
	            } else if (item > 85) {
	                counter = counter + 1;
	            } else {
	                counter = 0;
	            };
	            if (counter >= 4) {
	                if (status == "good/green") {
	                    status = "warning/orange";
	                }
	            }
			}
			
			machineTeperature.put(entry.getKey(), status);
		            
	
		}
		

		return machineTeperature;
	}

	@Override
	public List<OEEModel> getOEEReport(String date) {
		List <OEEModel> OEEModel=new ArrayList<OEEModel>();
		HashMap<String,Double> listOEEModel=new HashMap<String, Double>();
		HashMap<String,Double> listPerformance=new HashMap<String, Double>();

		HashMap<String,Double> listUptimePercentage=new HashMap<String, Double>();


		// we get date from front end as 2018-01-07
		String inputDate = date + " 00:00:00";
		
		//Performance% = actual gross production / norm gross production=30.000 * 100% 
		
		Date currentDate;
		try {
			currentDate = dateFormat.parse(inputDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate);
			
			String dateFrom = dateFormat.format(cal.getTime());
			cal.add(Calendar.DAY_OF_WEEK, 1);
			String dateTo = dateFormat.format(cal.getTime());
			System.out.println("dateFrom "+dateFrom);
			
			System.out.println("dateTo "+dateTo);
			
			
			
			List<MachineTotalProjection> listGrossProduction=productionRepository.getTotalProducedGroupByMachineName("PRODUCTION", dateFrom, dateTo);
			List<MachineTotalProjection> listSCRAPProduction=productionRepository.getTotalProducedGroupByMachineName("SCRAP", dateFrom, dateTo);

			int count=0;
			for(MachineTotalProjection item : listGrossProduction) {
				Double performance=item.getTotal()/(30000*24);
				listPerformance.put(item.getMachineName(), performance);
				
				
				MachineTotalProjection uptime=productionRepository.getTotalMachineIsrunning(item.getMachineName(), dateFrom, date+" 23:55:00",1);
				MachineTotalProjection lastDuration=productionRepository.getLastDurationIsrunning(item.getMachineName(), dateTo);
				
				System.out.println("uptime "+uptime.getTotal());
				System.out.println("lastDuration"+lastDuration.getTotal());
				
				Double uptimeValue=(uptime.getTotal()+lastDuration.getTotal())/1440;
				listUptimePercentage.put(item.getMachineName(),uptimeValue);
				
				System.out.println("getMachineName "+item.getMachineName()+ "  "+uptimeValue);
				OEEModel OEEMoelItem=new OEEModel();
				
				
				
				Double quality=(item.getTotal()-listSCRAPProduction.get(count).getTotal())/item.getTotal();
				
				OEEMoelItem.setMACHINE(item.getMachineName());
				OEEMoelItem.setDATETIME_FROM(dateTo);
				OEEMoelItem.setDATETIME_TO(dateFrom);
				
				
				OEEMoelItem.setPERFORMANCE(performance);
				OEEMoelItem.setAVAILABILITY(uptimeValue);
				OEEMoelItem.setQUALITY(quality);
				
				Double OEE=quality*uptimeValue*performance;
				OEEMoelItem.setOEE(OEE);
				
				OEEModel.add(OEEMoelItem);
				
				count++;
			}
			
			System.out.println("listPerformance"+listPerformance);
			System.out.println("listUptimePercentage"+listUptimePercentage);

			
			
			//UPTime
			

			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return OEEModel;
	}

	@Override
	public List<StatUsesModel> getStatusUsesReport(String date) {
		List<StatUsesModel> listStatUsesModel = new ArrayList<StatUsesModel>();

		HashMap<String, TreeMap<Integer, Double>> listNetProducedPerHour = new HashMap<String, TreeMap<Integer, Double>>();

		// we get date from front end as 2018-01-07
		String inputDate = date + " 00:00:00";

		Date currentDate;
		try {
			currentDate = dateFormat.parse(inputDate);

			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate);

			HashMap<String, Double> listNtProductionPerHour = new HashMap<String, Double>();

			String dateFrom = dateFormat.format(cal.getTime());
			cal.add(Calendar.DAY_OF_WEEK, 1);
			String dateTo = dateFormat.format(cal.getTime());
			cal.add(Calendar.DAY_OF_WEEK, -1);

			List<MachineTotalProjection> listMachinesTotalProduction = productionRepository
					.getTotalProducedGroupByMachineName("PRODUCTION", dateFrom, dateTo);
			List<MachineTotalProjection> listMachinesTotalScrap = productionRepository
					.getTotalProducedGroupByMachineName("SCRAP", dateFrom, dateTo);

			List<HashMap<String, Double>> boxTwoHashMap = calculateNetProductionAndScrapPercentage(
					listMachinesTotalProduction, listMachinesTotalScrap);

			HashMap<String, Double> listNetProduced = boxTwoHashMap.get(0);
			HashMap<String, Double> SCRAP_PERCENTAGE = boxTwoHashMap.get(1);

			System.out.println("listNetProduced = " + listNetProduced);
			System.out.println("SCRAP_PERCENTAGE = " + SCRAP_PERCENTAGE);

			// calculate downTime
			List<MachineTotalProjection> totalMachineDownByIsRunningStatus = productionRepository
					.getTotalMachineDownByIsRunningStatus(dateFrom, dateTo);
			List<MachineTotalProjection> totalMachineDown = productionRepository.getTotalMachineDownByIsRunningStatus(dateFrom, dateTo);

			HashMap<String, Double> listDownTimePercentage = getDownTimePercentage(totalMachineDownByIsRunningStatus,
					totalMachineDown);

			for (MachineTotalProjection machineTotalProjection : listMachinesTotalProduction) {
				listNetProducedPerHour.put(machineTotalProjection.getMachineName(), new TreeMap<Integer, Double>());

				StatUsesModel statUsesModel = new StatUsesModel();

				statUsesModel.setDATETIME_FROM(dateFrom);
				statUsesModel.setDATETIME_TO(dateTo);
				statUsesModel.setMACHINE(machineTotalProjection.getMachineName());
				listStatUsesModel.add(statUsesModel);

			}

			// Loop 24 Times => 24 hours

			for (int H = 0; H < 24; H++) {
				dateFrom = dateFormat.format(cal.getTime());
				cal.add(Calendar.HOUR_OF_DAY, 1);
				dateTo = dateFormat.format(cal.getTime());

				List<MachineTotalProjection> listMachinesTotalProductionByHour = productionRepository
						.getTotalProducedGroupByMachineName("PRODUCTION", dateFrom, dateTo);
				List<MachineTotalProjection> listMachinesTotalScrapByHour = productionRepository
						.getTotalProducedGroupByMachineName("SCRAP", dateFrom, dateTo);

				int sizeOfList = listMachinesTotalProductionByHour.size();

				for (int k = 0; k < sizeOfList; k++) {
					Double netProduced = listMachinesTotalProductionByHour.get(k).getTotal()
							- listMachinesTotalScrapByHour.get(k).getTotal();

					listNetProducedPerHour.get(listMachinesTotalProductionByHour.get(k).getMachineName()).put(H,
							netProduced);
				}

			}
			for (MachineTotalProjection machineTotalProjection : listMachinesTotalProduction) {

				TreeMap<Integer, Double> listNetProducedPerHourMap = listNetProducedPerHour
						.get(machineTotalProjection.getMachineName());

				TreeMap<Integer, Double> treeMap = new TreeMap<Integer, Double>(listNetProducedPerHourMap);
				listNetProducedPerHour.replace(machineTotalProjection.getMachineName(), treeMap);

			}

			for (StatUsesModel statUsesModel1 : listStatUsesModel) {
				statUsesModel1.setListNetproductionPerHoure(listNetProducedPerHour.get(statUsesModel1.getMACHINE()));
				statUsesModel1.setPRODUCTION(listNetProduced.get(statUsesModel1.getMACHINE()));
				statUsesModel1.setSCRAP_PERCENTAGE(SCRAP_PERCENTAGE.get(statUsesModel1.getMACHINE()));
				statUsesModel1.setDOWNTIME_PERCENTAGE(listDownTimePercentage.get(statUsesModel1.getMACHINE()));

			}
			System.out.println("listNetProducedPerHour Sorted=>>>" + listNetProducedPerHour);
			System.out.println("statUsesModel1 " + listStatUsesModel);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return listStatUsesModel;
	}

	private HashMap<String, Double> getDownTimePercentage(
			List<MachineTotalProjection> totalMachineDownByIsRunningStatus,
			List<MachineTotalProjection> totalMachineDown) {

		HashMap<String, Double> downTimePERCENTAGE = new HashMap<String, Double>();

		System.out.println("totalMachineDownByIsRunningStatus " + totalMachineDownByIsRunningStatus);
		System.out.println("totalMachineDown " + totalMachineDown);

		int sizeOfList = totalMachineDownByIsRunningStatus.size();

		for (int k = 0; k < sizeOfList; k++) {

			Double valueP = totalMachineDownByIsRunningStatus.get(k).getTotal() / totalMachineDown.get(k).getTotal();
			downTimePERCENTAGE.put(totalMachineDownByIsRunningStatus.get(k).getMachineName(), valueP);
		}

		return downTimePERCENTAGE;
	}

	private ArrayList<HashMap<String, Double>> calculateNetProductionAndScrapPercentage(
			List<MachineTotalProjection> listMachinesTotalProduction,
			List<MachineTotalProjection> listMachinesTotalScrap) {

		ArrayList<HashMap<String, Double>> boxTwoHashMap = new ArrayList<HashMap<String, Double>>();

		HashMap<String, Double> listNetProduced = new HashMap<String, Double>();
		HashMap<String, Double> SCRAP_PERCENTAGE = new HashMap<String, Double>();

		// To not calculate every Time the size of the list
		int sizeOfList = listMachinesTotalProduction.size();

		for (int i = 0; i < sizeOfList; i++) {
			Double netProduced = listMachinesTotalProduction.get(i).getTotal()
					- listMachinesTotalScrap.get(i).getTotal();
			listNetProduced.put(listMachinesTotalProduction.get(i).getMachineName(), netProduced);

			Double percentage = listMachinesTotalScrap.get(i).getTotal()
					/ listMachinesTotalProduction.get(i).getTotal();

			SCRAP_PERCENTAGE.put(listMachinesTotalProduction.get(i).getMachineName(), percentage);

		}
		boxTwoHashMap.add(listNetProduced);
		boxTwoHashMap.add(SCRAP_PERCENTAGE);

		return boxTwoHashMap;
	}

}
