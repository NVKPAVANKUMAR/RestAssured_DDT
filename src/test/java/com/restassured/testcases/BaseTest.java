package com.restassured.testcases;

import com.aventstack.extentreports.Status;
import com.restassured.constants.Constants;
import com.restassured.reports.ExtentReport;
import com.restassured.reports.LogStatus;
import com.restassured.utils.Log;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.restassured.reports.ExtentTestManager.getTest;
import static io.restassured.RestAssured.given;

public class BaseTest {

    protected StringWriter writer;
    protected PrintStream printStrObj;
    private static final Logger Log = Logger.getLogger(BaseTest.class.getName());

    /*
     * Initializing the extent report
     * @author NVK PAVANKUMAR
     */
    @BeforeSuite
    public void setUpSuite() {
        ExtentReport.createExtent();
    }

    /*
     * Flushing the extent report
     * Opening the extent report automatically after the test suite execution.
     * @author NVK PAVANKUMAR
     */

    @AfterSuite
    public void afterSuite() throws IOException {
        ExtentReport.extent.flush();
        File htmlFile = new File(Constants.EXTENTREPORTPATH);
        Desktop.getDesktop().browse(htmlFile.toURI());
    }

    /*
     * This method helps to write the request and reponse to the extent report
     * @author NVK PAVANKUMAR
     */
    @BeforeMethod
    public void setUp() {
        writer = new StringWriter();
        printStrObj = new PrintStream(new WriterOutputStream(writer), true);
    }

    /*
     * Provided as an sample to handle bearer token based scenarios and to handle x-www-form-urlencoded content type.
     * Perform auth once before class
     * @author : NVK PAVANKUMAR
     */
    protected void performAuth() {
        Response response = given().header("Content-Type", "application/json").
                config(RestAssured.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs("x-www-form-urlencoded", ContentType.URLENC)))
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("username", Constants.USERNAME)
                .formParam("authType", Constants.AUTHTYPE)
                .formParam("password", Constants.PASSWORD)
                .post(Constants.BASEURL + Constants.AUTH_ENDPOINT);
        response.then().statusCode(201);
        //extract().path("the path to token");
        System.out.println("AUTH Successful");
    }

    /*
     * Format the api string and log in Extent Report
     * @author : NVK PAVANKUMAR
     * @param  : content
     */
    protected void formatAPIAndLogInReport(String content) {
        String prettyPrint = content.replace("\n", "<br>");
        getTest().log(Status.INFO,"<pre>" + prettyPrint + "</pre>");
    }

    /*
     * Read the json file and convert to String
     * @author : NVK PAVANKUMAR
     * @param  : filepath
     */
    public String generateStringFromResource(String path) throws IOException {
        String temp = "";
        try {
            temp = new String(Files.readAllBytes(Paths.get(path)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    public void writeRequestAndResponseInReport(String request, String response) {
        getTest().log(Status.INFO, "---- Request ---");
        formatAPIAndLogInReport(request);
        getTest().log(Status.INFO,"---- Response ---");
        formatAPIAndLogInReport(response);
    }
}