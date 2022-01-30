package com.restassured.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.restassured.constants.Constants;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;


public class ExtentReport {
    public static ExtentReports extent=null;

    static final Logger logger = LogManager.getLogger(ExtentManager.class);

    //To avoid external initialization
    public static ExtentReports createExtent() {
        String fileName = Constants.EXTENTREPORTPATH;

        ExtentSparkReporter reporter = new ExtentSparkReporter(fileName);
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setDocumentTitle(Constants.reportFileName);
        reporter.config().setEncoding("utf-8");
        reporter.config().setReportName(Constants.reportFileName);
        reporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Username", System.getProperty("user.name"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("OS Version", System.getProperty("os.version"));
        extent.setSystemInfo("Env", "QA");
        extent.setSystemInfo("Java", System.getProperty("java.version"));
        return extent;
    }

    /**
     * Create the report path
     *
     * @param path - path to store the extent report
     * @return reportFileLocation
     */
    private static String getReportPath(String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                logger.info("Directory: " + path + " is created!");
                return Constants.EXTENTREPORTPATH;
            } else {
                logger.info("Failed to create directory: " + path);
                return System.getProperty("user.dir");
            }
        } else {
            logger.info("Directory already exists: " + path);
        }
        return Constants.EXTENTREPORTPATH;
    }

}
