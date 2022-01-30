package com.restassured.reports;

import com.aventstack.extentreports.Status;

import static com.restassured.reports.ExtentTestManager.getTest;

public class LogStatus {

    private LogStatus() {

    }

    public static void pass(String message) {
        getTest().log(Status.PASS, message);
    }

    public static void fail(String message) {
        getTest().log(Status.FAIL, message);
    }

    public static void info(String message) { getTest().log(Status.INFO, message); }

    public static void skip(String message) {
        getTest().log(Status.SKIP, message);
    }

    public static void warning(String message) {
        getTest().log(Status.WARNING, message);
    }

}
