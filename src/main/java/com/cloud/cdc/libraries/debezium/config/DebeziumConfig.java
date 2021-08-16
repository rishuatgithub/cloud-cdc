package com.cloud.cdc.libraries.debezium.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
public class DebeziumConfig {

    @Value("${employee.datasource.host}")
    private String employeeDbHost;
    @Value("${employee.datasource.port}")
    private String employeeDbPort;
    @Value("${employee.datasource.database}")
    private String employeeDbName;
    @Value("${employee.datasource.username}")
    private String employeeDbUser;
    @Value("${employee.datasource.password}")
    private String employeeDbPassword;

    @Bean
    public io.debezium.config.Configuration employeeConnector() throws IOException {
        File offsetStorageTempFile = File.createTempFile("offsets_", ".dat");
        File dbHistoryTempFile = File.createTempFile("dbhistory_", ".dat");
        return io.debezium.config.Configuration.create()
                .with("name","employee-connector")
                .with("connector.class", "io.debezium.connector.mysql.MySqlConnector")
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", offsetStorageTempFile.getAbsolutePath())
                .with("offset.flush.interval.ms", "60000")
                .with("database.hostname", employeeDbHost)
                .with("database.port", employeeDbPort)
                .with("database.user", employeeDbUser)
                .with("database.password", employeeDbPassword)
                .with("database.dbname", employeeDbName)
                .with("database.include.list", employeeDbName)
                .with("include.schema.changes", "false")
                .with("database.allowPublicKeyRetrieval", "true")
                .with("database.server.id", "10181")
                .with("database.server.name", "customer-mysql-db-server")
                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
                .with("database.history.file.filename", dbHistoryTempFile.getAbsolutePath())
                .build();
    }

}
