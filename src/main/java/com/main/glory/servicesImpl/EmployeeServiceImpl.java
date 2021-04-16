package com.main.glory.servicesImpl;

import com.main.glory.Dao.admin.EmployeeSequenceDao;
import com.main.glory.Dao.employee.AttendanceDao;
import com.main.glory.Dao.employee.EmployeeDataDao;
import com.main.glory.Dao.employee.EmployeeMastDao;
import com.main.glory.model.admin.EmployeeSequence;
import com.main.glory.model.employee.Attendance;
import com.main.glory.model.employee.EmployeeData;
import com.main.glory.model.employee.EmployeeMast;
import com.main.glory.model.employee.response.EmployeeAttendanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("employeeServiceImpl")
public class EmployeeServiceImpl {

    @Autowired
    EmployeeDataDao employeeDataDao;

    @Autowired
    EmployeeMastDao employeeMastDao;

    @Autowired
    AttendanceDao attendanceDao;

    @Autowired
    EmployeeSequenceDao employeeSequenceDao;

    @Transactional
    public Long addEmployeeRecord(EmployeeMast record) throws Exception {

        //employee id should in within 4 digit for qr code so maintain the employee id
        EmployeeSequence employeeSequenceExist = employeeSequenceDao.getEmployeeSequence();
        if(employeeSequenceExist==null)
        {
            EmployeeSequence employeeSequence = new EmployeeSequence(1001l);
            employeeSequenceExist =  employeeSequenceDao.save(employeeSequence);

        }


         if(record.getEmployeeDocumentList().isEmpty())
             throw new Exception("employee document can't be null");


        record.setEmpId(employeeSequenceExist.getEmpId());
        EmployeeMast x = employeeMastDao.saveAndFlush(record);

        //employeeDataDao.saveAll(record.getEmployeeDocumentList());

        //update the employee sequnce
        employeeSequenceExist.setEmpId(employeeSequenceExist.getEmpId()+1);
        employeeSequenceDao.saveAndFlush(employeeSequenceExist);
        return x.getEmpId();
    }



    public Long updateEmployeeRecord(EmployeeMast record) throws Exception {
        //find the record

       /* EmployeeMast employeeMastExist = employeeMastDao.getEmployeeByAadhaarExceptId(record.getAadhaar(),record.getId());
        if(employeeMastExist!=null)
            throw new Exception("employee exist with aadhaar number");*/

        List<EmployeeData> documentList = employeeDataDao.getEmployeeDataByEmployeeId(record.getId());
        EmployeeMast x = employeeMastDao.saveAndFlush(record);
        for(EmployeeData employeeData:documentList)
        {
            employeeData.setControlId(x.getId());
            employeeDataDao.saveAndFlush(employeeData);
        }

        //employeeDataDao.saveAll(record.getEmployeeDocumentList());
        return x.getId();

    }

    public EmployeeMast getEmployeeById(Long id) {
        return employeeMastDao.getEmployeeById(id);
    }

    public List<EmployeeMast> getAllEmployee() {
        return employeeMastDao.getAllEmployee();
    }

    public void deleteEmployeeById(Long id) throws Exception {
        EmployeeMast employeeMast = employeeMastDao.getEmployeeByEmpId(id);
        if(employeeMast==null)
            throw new Exception("no employee found");

        List<Attendance> attendances = attendanceDao.getAllAttendanceByEmployeeId(id);
        if(!attendances.isEmpty())
            throw new Exception("remove the record of attendance");

        employeeMastDao.deleteByEmployeeId(id);
    }

    public Long addEmployeeDataRecord(EmployeeData record) {
        EmployeeData employeeData = employeeDataDao.saveAndFlush(record);
        return employeeData.getId() ;
    }


    public EmployeeMast getEmployeeByEmpId(Long id) {
        return employeeMastDao.getEmployeeByEmpId(id);
    }

    public List<EmployeeMast> getEmployeeByEmpIdOrName(String id) {
        //check that the id is id or name
        List<EmployeeMast> employeeMastList=new ArrayList<>();
        try
        {
            employeeMastList = employeeMastDao.getEmployeeByLikeEmpId(Long.parseLong(id));

            return employeeMastList;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            employeeMastList = employeeMastDao.getEmployeeByName(id);
            return employeeMastList;
        }
    }
}
