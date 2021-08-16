package com.cloud.cdc.libraries.debezium.service;

import com.cloud.cdc.libraries.debezium.entity.Employee;
import com.cloud.cdc.libraries.debezium.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.data.Envelope.Operation;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void replicateData(Map<String, Object> employeeData, Operation operation){
        final ObjectMapper mapper = new ObjectMapper();
        final Employee employee = mapper.convertValue(employeeData, Employee.class);

        if (operation.DELETE == operation){
            employeeRepository.deleteById(employee.getEmployeeID());
        }else{
            employeeRepository.save(employee);
        }
    }
}
