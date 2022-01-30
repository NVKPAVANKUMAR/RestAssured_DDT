package com.restassured.listeners;

import com.aventstack.extentreports.Status;
import com.restassured.reports.ExtentManager;
import com.restassured.utils.Log;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static com.restassured.reports.ExtentTestManager.getTest;

/*
 * Listener class which is implementing ITestListener and hence we can use this to dynamically create reports, write logs.
 */
public class ITestListenerClass implements ITestListener {

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	@Override
	public void onStart(ITestContext iTestContext) {
		Log.info("I am in onStart method " + iTestContext.getName());
	}

	@Override
	public void onFinish(ITestContext iTestContext) {
		Log.info("I am in onFinish method " + iTestContext.getName());
		//ExtentManager.getInstance().flush();
	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
		Log.info(getTestMethodName(iTestResult) + " test is starting.");
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		Log.info(getTestMethodName(iTestResult) + " test is succeed.");
		getTest().log(Status.PASS, "Test passed");
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		Log.info(getTestMethodName(iTestResult) + " test is failed.");
		getTest().log(Status.FAIL, "Test Failed");
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		Log.info(getTestMethodName(iTestResult) + " test is skipped.");
	//	getTest().log(Status.SKIP, "Test Skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		Log.info("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
	}
}
