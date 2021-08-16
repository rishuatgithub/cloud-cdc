package com.cloud.cdc.libraries.debezium.listener;

import com.cloud.cdc.libraries.debezium.service.EmployeeService;
import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class EmployeeDebzListener {

    private final Executor executor = Executors.newSingleThreadExecutor();
    private final EmployeeService employeeService;
    private final DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;

    public EmployeeDebzListener(Configuration employeeConnectorConfig, EmployeeService employeeService){
        this.debeziumEngine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(employeeConnectorConfig.asProperties())
                .notifying(this::handleChangeEvent)
                .build();

        this.employeeService = employeeService;
    }

    private void handleChangeEvent(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent){
        SourceRecord sourceRecord = sourceRecordRecordChangeEvent.record();

        log.info("Key = ["+sourceRecord.key()+"] value=["+sourceRecord.value()+"]");

    }

    @PostConstruct
    private void start() {
        this.executor.execute(debeziumEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        if (this.debeziumEngine != null) {
            this.debeziumEngine.close();
        }
    }
}
