package com.cloud.cdc.libraries.debezium.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Employee {
    @Id
    private Long EmployeeID;
    private String EmployeeName;
    private String EmployeeEmail;
}
