package com.aepl.reports;

import java.io.File;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * ExtentManager class is responsible for managing the ExtentReports instance.
 * It provides methods to create, retrieve, and flush the ExtentReports object 
 * used to generate detailed and visually rich test reports.
 *
 * <p>
 * Key Responsibilities:
 * <ul>
 *   <li>Creates a single instance of ExtentReports to ensure a singleton pattern.</li>
 *   <li>Attaches an ExtentSparkReporter for generating reports in HTML format.</li>
 *   <li>Configures report properties such as document title, report name, and theme.</li>
 *   <li>Adds system-level information like environment, user, and operating system to the report.</li>
 * </ul>
 *
 * <p>
 * Usage:
 * <ul>
 *   <li>Call {@link #createInstance()} to initialize the ExtentReports object.</li>
 *   <li>Use {@link #getInstance()} to retrieve the singleton ExtentReports object.</li>
 *   <li>Call {@link #flush()} after all tests are executed to finalize the report.</li>
 * </ul>
 */
public class ExtentManager {

    private static ExtentReports extent;

    /**
     * Creates and initializes a new instance of ExtentReports.
     * <p>
     * This method performs the following:
     * <ul>
     *   <li>Defines the file path for the HTML report.</li>
     *   <li>Configures the ExtentSparkReporter for generating reports with custom properties.</li>
     *   <li>Attaches the reporter to the ExtentReports instance.</li>
     *   <li>Sets additional system-level information in the report, such as environment and OS.</li>
     * </ul>
     * 
     * @return an initialized instance of ExtentReports.
     * @throws IllegalStateException if the report file cannot be created or accessed.
     */
    public static ExtentReports createInstance() {
        if (extent != null) {
            throw new IllegalStateException("ExtentReports instance already created. Use getInstance() instead.");
        }

        String filePath = System.getProperty("user.dir") + "/test-results/ExtentReport.html";

        // Validate that the file directory exists or can be created
        File reportFile = new File(filePath);
        if (!reportFile.getParentFile().exists() && !reportFile.getParentFile().mkdirs()) {
            throw new IllegalStateException("Failed to create directories for report file: " + filePath);
        }

        // Use ExtentSparkReporter for modern and visually rich HTML reports
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(filePath);
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Test Execution Report");
        sparkReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.STANDARD); // Optional

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Environment", System.getenv("ENVIRONMENT") != null ? System.getenv("ENVIRONMENT") : "QA");
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setAnalysisStrategy(AnalysisStrategy.SUITE); // added new 

        return extent;
    }

    /**
     * Retrieves the singleton instance of ExtentReports.
     * <p>
     * If the instance has not been created yet, it initializes the ExtentReports
     * by calling {@link #createInstance()}.
     * 
     * @return the singleton instance of ExtentReports.
     */
    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    /**
     * Finalizes the ExtentReports and writes the report data to the file.
     * <p>
     * This method should be called after all test executions to ensure the report
     * is properly written and saved.
     * 
     * @throws IllegalStateException if the ExtentReports instance is null.
     */
    public static void flush() {
        if (extent == null) {
            throw new IllegalStateException("ExtentReports instance is null. Cannot flush reports.");
        }
        extent.flush();
    }
}
