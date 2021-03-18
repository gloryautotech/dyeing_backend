package com.main.glory.model.employee.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDocument {
    Long id;
    String name;
    String url;
    String type;
    Long controlId;
}
