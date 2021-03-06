package com.main.glory.model.employee.response;

import com.main.glory.model.employee.Attendance;
import com.main.glory.model.employee.EmployeeMast;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetAllEmployee extends EmployeeMast {
    String departmentName;
    List<Attendance> attendanceLists;

    public GetAllEmployee(EmployeeMast e,String departmentName) {
        super(e);
        this.departmentName = departmentName;
    }

    public GetAllEmployee(GetAllEmployee e, List<Attendance> attendances) {
        super(e);
        this.departmentName = e.departmentName;
        this.attendanceLists = attendances;
    }

   /* public GetAllEmployee(EmployeeMast e,String departmentName,List<Attendance> attendanceLists) {
        super(e);
        this.departmentName = departmentName;
        this.attendanceLists = attendanceLists;
    }*/
}
