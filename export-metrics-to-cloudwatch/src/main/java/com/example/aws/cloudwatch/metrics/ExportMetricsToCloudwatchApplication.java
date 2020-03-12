package com.example.aws.cloudwatch.metrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExportMetricsToCloudwatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExportMetricsToCloudwatchApplication.class, args);
    }

}
