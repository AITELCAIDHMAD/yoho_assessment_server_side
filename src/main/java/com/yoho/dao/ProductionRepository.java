package com.yoho.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yoho.entities.Production;
import com.yoho.projections.MachineTemperatureProjection;
import com.yoho.projections.MachineTotalProjection;

public interface ProductionRepository extends JpaRepository<Production	, Long> {
	
	@Query(value="select machine_name as machineName, SUM(value) as total from Production where variable_name=?1 and datetime_from>=?2 and datetime_to<=?3 group by machine_name",nativeQuery = true)
	List<MachineTotalProjection> getTotalProducedGroupByMachineName(String variable_name,String dateFrom,String dateTo);
	

	@Query(value="select machine_name as machineName,count(isrunning) as total  from Runtime where isrunning=0 and datetime>=?1 and datetime<=?2 group by machine_name",nativeQuery = true)
	List<MachineTotalProjection> getTotalMachineDownByIsRunningStatus(String dateFrom,String dateTo);
	
	@Query(value="SELECT sum(TIMESTAMPDIFF (MINUTE,(SELECT b.datetime FROM Runtime b WHERE b.id < a.id AND machine_name = ?1 AND (datetime BETWEEN ?2 AND ?3 ) ORDER BY id DESC LIMIT 1), a.datetime)) AS total FROM Runtime a where (datetime BETWEEN ?2 AND ?3) AND isrunning = ?4 ORDER BY machine_name,a.id",nativeQuery = true)
	MachineTotalProjection getTotalMachineIsrunning(String machine_name,String dateFrom,String dateTo,int isrunning);
	
	@Query(value="SELECT TIMESTAMPDIFF (MINUTE, datetime, ?2) as total, isrunning as isRunning FROM Runtime WHERE machine_name = ?1 ORDER BY id DESC LIMIT 1",nativeQuery = true)
	MachineTemperatureProjection getLastDurationIsrunning(String machine_name,String dateTo);

	@Query(value="select value as total from Production where variable_name='CORE TEMPERATURE' and datetime_from>=?2 and datetime_to<=?3 and machine_name=?1 ORDER BY datetime_from",nativeQuery = true)
	List<MachineTotalProjection> getTemerature(String macine_name,String dateFrom, String dateTo);
	
	
	@Query(value="select machine_name as machineName from Production group by machine_name",nativeQuery = true)
	List getListMachine();
	
	@Query(value="select CONCAT(datetime,'') as time from Runtime where isrunning=?1 and machine_name=?2 and datetime between ?3 and ?4",nativeQuery = true)
	List getListTime(int isRunning,String machine_name,String dateTo,String dateFrom);
	
	
	
	
	
	
	// OEM
	
	
	
	
	
	
	
	
	
}