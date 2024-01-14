package com.api.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.entities.Holiday;

public interface HolidayRepo extends JpaRepository<Holiday, Integer> {

	// Get Holiday of year
//	@Query(value = "select count(holiday_date) as noOfHoliday,year(holiday_date) as yearName from holiday group by yearName;", nativeQuery = true)
	@Query(value = "select count(holiday_date) as noOfHoliday,EXTRACT(YEAR from holiday_date) as yearName from holiday group by yearName;", nativeQuery = true)
	List<Object[]> getHolidayOfAllOverYears();

	// Get Holiday of year
//	@Query(value = "select count(holiday_date) as noOfHoliday,year(holiday_date) as yearName from holiday where YEAR(holiday_date)>=:startYear and YEAR(holiday_date)<=:endYear group by yearName;", nativeQuery = true)
	@Query(value = "select count(holiday_date) as noOfHoliday,EXTRACT(YEAR from holiday_date) as yearName from holiday where EXTRACT(YEAR from holiday_date)>=:startYear and EXTRACT(YEAR from holiday_date)<=:endYear group by yearName;", nativeQuery = true)
	List<Object[]> getHolidayOfProjectYears(@Param("startYear") int startYear, @Param("endYear") int endYear);

	// Get Holiday of year
//MySQL	@Query(value = "SELECT COUNT(holiday_date) as noOfHoliday ,YEAR(holiday_date) as yearName FROM holiday WHERE YEAR(holiday_date) =:yearArg GROUP BY YEAR(holiday_date);", nativeQuery = true)
	@Query(value = "SELECT COUNT(holiday_date) as noOfHoliday ,EXTRACT(YEAR from holiday_date) as yearName FROM holiday WHERE EXTRACT(YEAR from holiday_date) =:yearArg GROUP BY EXTRACT(YEAR from holiday_date);", nativeQuery = true)
	List<Object[]> getHolidayOfYearWise(@Param("yearArg") int year);

	// Get Holidays of month of a year
//	@Query(value = "SELECT COUNT(holiday_date) as noOfHoliday ,MONTH(holiday_date) as monthName FROM holiday WHERE YEAR(holiday_date) =:yearArg GROUP BY MONTH(holiday_date);", nativeQuery = true)
	@Query(value = "SELECT COUNT(holiday_date) as noOfHoliday ,EXTRACT(Month from holiday_date) as monthName FROM holiday WHERE EXTRACT(Year from holiday_date) =:yearArg GROUP BY EXTRACT(Month from holiday_date);", nativeQuery = true)
	List<Object[]> getHolidayOfMonthOfYear(@Param("yearArg") int year);

	// Get Holidays of weeks of a year
//	@Query(value = "SELECT COUNT(holiday_date) as noOfHoliday , WEEK(holiday_date) as weekName FROM holiday WHERE YEAR(holiday_date)=:yearArg GROUP BY WEEK(holiday_date)", nativeQuery = true)
	@Query(value = "SELECT COUNT(holiday_date) as noOfHoliday ,EXTRACT(WEEK from holiday_date) as weekName FROM holiday WHERE EXTRACT(Year from holiday_date)=:yearArg GROUP BY EXTRACT(Week from holiday_date);", nativeQuery = true)
	List<Object[]> getHolidayOfWeekOfYear(@Param("yearArg") int year);

	// Get Holidays of a particular week of a year
//	@Query(value = "SELECT EXTRACT(isodow from holiday_date) from holiday where EXTRACT(WEEK from holiday_date)=:weekArg and EXTRACT(Year from holiday_date)=:yearArg;", nativeQuery = true)
	@Query(value = "SELECT EXTRACT(isodow from holiday_date) from holiday where EXTRACT(WEEK from holiday_date)=:weekArg and EXTRACT(YEAR from holiday_date) =:yearArg", nativeQuery = true)
	List<Object[]> getHolidayOfWeekOfYearTimeSheet(@Param("weekArg") int week, @Param("yearArg") int year);

	// Get Holidays by date
//	@Query(value = " SELECT DAYNAME(holiday_date) from holiday where WEEK(holiday_date)=:weekArg and YEAR(holiday_date)=:yearArg", nativeQuery = true)
	@Query(value = " SELECT to_char(holiday_date, 'Day') from holiday where EXTRACT(WEEK from holiday_date)=:weekArg and EXTRACT(YEAR from holiday_date) =:yearArg", nativeQuery = true)
	List<Object[]> getHolidayByDate(@Param("weekArg") int week, @Param("yearArg") int year);

	boolean existsByHolidayDate(Date holidayDate);
}
