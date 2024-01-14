package com.api.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import com.api.entities.Employee;
import com.api.entities.TimeSheet;

@EnableJpaRepositories
public interface TimeSheetRepo extends JpaRepository<TimeSheet, Integer> {
	
	
	
	TimeSheet findByTimesheetid(int timesheetid);

	List<TimeSheet> findByProjectId(int projectId);

	List<TimeSheet> findByEmployeeObj(Employee employeeObj);

//	@Query(value = "SELECT YEAR(fill_date) AS Year,SUM(workinghour) AS Hours,project_id FROM time_sheet where project_id=:pid GROUP BY YEAR(fill_date);", nativeQuery = true)
	@Query(value = "SELECT EXTRACT(YEAR from fill_date) AS YearName,SUM(workinghour) AS Hours FROM time_sheet where project_id=:pid GROUP BY YearName;", nativeQuery = true)
	List<Object[]> getWokingHourByYear(@Param("pid") int projectId);

//	@Query(value = "SELECT MONTH(fill_date) AS month, YEAR(fill_date) as yearName ,SUM(workinghour) as Hours,project_id from time_sheet where YEAR(fill_date)=:yearArg and project_id=:pid group by Year(fill_date), MONTH(fill_date);", nativeQuery = true)
	@Query(value = "SELECT EXTRACT(Month from fill_date) AS monthName, EXTRACT(YEAR from fill_date) as yearName ,SUM(workinghour) as Hours from time_sheet where EXTRACT(YEAR from fill_date)=:yearArg and project_id=:pid group by yearName, monthName;", nativeQuery = true)
	List<Object[]> getWokingHourByMonth(@Param("pid") int projectId, @Param("yearArg") int year);

	// To find particular project working hours in particular week of a year
//	@Query(value = "SELECT WEEK(fill_date) AS week, YEAR(fill_date) as yearName ,SUM(workinghour) as Hours,project_id from time_sheet where YEAR(fill_date)=:yearArg and project_id=:pid group by Year(fill_date), WEEK(fill_date);", nativeQuery = true)
	@Query(value = "SELECT EXTRACT(WEEK from fill_date) AS weekName, EXTRACT(YEAR from fill_date) as yearName ,SUM(workinghour) as Hours from time_sheet where EXTRACT(YEAR from fill_date)=:yearArg and project_id=:pid group by yearName, weekName;", nativeQuery = true)
	List<Object[]> getWokingHourByWeek(@Param("pid") int projectId, @Param("yearArg") int year);

	// Get Timesheet fill by week of a year
//	@Query(value = "SELECT fill_date from time_sheet where emp_id=:empId ORDER BY timesheetid DESC LIMIT 1;", nativeQuery = true)
	@Query(value = "SELECT fill_date from time_sheet where emp_id=:empId ORDER BY timesheetid DESC LIMIT 1;;", nativeQuery = true)
	List<Object[]> getTimesheetPreviousFilledDate(@Param("empId") int eid);

	// Checking Employee filled timesheet of current week or not
	@Query(value = "SELECT EXTRACT(WEEK from fill_date),fill_date from time_sheet where emp_id=:eid and EXTRACT(WEEK from fill_date)=:weekArg ;", nativeQuery = true)
	List<Object[]> getWeekOfTSFilledByEMployee(@Param("eid") int employeeId, @Param("weekArg") int week);

}
