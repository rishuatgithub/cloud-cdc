package com.cloud.cdc.libraries.debezium.repository;

import com.cloud.cdc.libraries.debezium.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
