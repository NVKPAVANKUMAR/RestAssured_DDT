package com.restassured.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentManager {

    private ExtentManager() {

    }

    public static final ThreadLocal<ExtentTest> exTest = new ThreadLocal<ExtentTest>();

    public static ExtentTest getExtTest() {
        return exTest.get();
    }

    public static void setExtentTest(ExtentTest test) {
        exTest.set(test);
    }

    private static ExtentReports extent;

    /**
     * Create an extent report instance
     *
     * @return ExtentReports
     */
    public static ExtentReports createInstance() {
        return extent;
    }
}
