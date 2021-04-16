
package com.main.glory.Dao.employee;

import com.main.glory.model.employee.EmployeeMast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface EmployeeMastDao extends JpaRepository<EmployeeMast,Long> {


    @Query("select e from EmployeeMast e where e.aadhaar=:adharNo AND e.id!=:l")
    EmployeeMast getEmployeeByAadhaarExceptId(String adharNo, long l);

    @Query("select s from EmployeeMast s where s.id=:id")
    EmployeeMast getEmployeeById(Long id);

    @Query("select s from EmployeeMast s ORDER BY s.id DESC")
    List<EmployeeMast> getAllEmployee();

    @Modifying
    @Transactional
    @Query("delete from EmployeeMast w where w.id=:id")
    void deleteByEmployeeId(Long id);

    @Query("select x from EmployeeMast x where x.empId=:empId")
    EmployeeMast getEmployeeByEmpId(Long empId);

    @Query("select x from EmployeeMast x where x.name LIKE :id%")
    List<EmployeeMast> getEmployeeByName(String id);

    @Query("select x from EmployeeMast x where x.empId LIKE ':empId%'")
    List<EmployeeMast> getEmployeeByLikeEmpId(Long empId);
}
