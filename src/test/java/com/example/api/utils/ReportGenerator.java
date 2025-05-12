package com.example.api.utils;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.Collections;

public class ReportGenerator {
    public static void generateReport() {
        File reportOutputDirectory = new File("reports");
        String jsonFilePath = "reports/cucumber.json";

        Configuration config = new Configuration(reportOutputDirectory, "API Test Suite");
        config.addClassifications("Platform", System.getProperty("os.name"));
        config.addClassifications("Java Version", System.getProperty("java.version"));
        config.addClassifications("Environment", "QA");

        ReportBuilder reportBuilder = new ReportBuilder(Collections.singletonList(jsonFilePath), config);
        reportBuilder.generateReports();
    }
}

