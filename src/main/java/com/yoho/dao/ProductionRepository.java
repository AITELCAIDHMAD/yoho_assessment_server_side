package com.yoho.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yoho.entities.Production;
import com.yoho.projections.MachineTotalProjection;

public interface ProductionRepository extends JpaRepository<Production, Long> {

	@Query(value = "select machine_name as machineName, SUM(value) as total from Production where variable_name=?1 and datetime_from>=?2 and datetime_to<=?3 group by machine_name", nativeQuery = true)
	List<MachineTotalProjection> getTotalProducedGroupByMachineName(String variable_name, String dateFrom,
			String dateTo);

	@Query(value = "select value as total from Production where variable_name='CORE TEMPERATURE' and datetime_from>=?2 and datetime_to<=?3 and machine_name=?1 ORDER BY datetime_from", nativeQuery = true)
	List<MachineTotalProjection> getTemerature(String macine_name, String dateFrom, String dateTo);

	@Query(value = "select machine_name as machineName from Production group by machine_name", nativeQuery = true)
	List getListMachine();

	@Query(value = "select CONCAT(datetime,'') as time from Runtime where isrunning=?1 and machine_name=?2 and datetime between ?3 and ?4", nativeQuery = true)
	List getListTime(int isRunning, String machine_name, String dateTo, String dateFrom);

}