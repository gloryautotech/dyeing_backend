package com.main.glory.Dao.machine;

import com.main.glory.model.machine.Thermopack;
import com.main.glory.model.machine.response.ThermopackFilterRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;
import java.util.List;

@EnableJpaRepositories
public interface ThermopackDao extends JpaRepository<Thermopack,Long> {
    @Query("select s from Thermopack s where s.controlId=:controlId AND s.dateToEnter <= :toDate AND s.dateToEnter>=:fromDate AND s.timeOf>=:fromTime AND s.timeOf<=:toTime AND s.controlId IS NOT NULL AND s.dateToEnter IS NOT NULL AND s.timeOf IS NOT NULL")
    List<Thermopack> findByControlIdAndTime(Long controlId, Long toTime, Long time, Date fromDate, Date toDate);
}
