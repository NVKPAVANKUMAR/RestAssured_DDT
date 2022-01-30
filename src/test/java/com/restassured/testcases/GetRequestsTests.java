package com.restassured.testcases;

import com.google.common.io.Files;
import com.restassured.constants.Constants;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import static com.restassured.reports.ExtentTestManager.startTest;
import static io.restassured.RestAssured.given;

public class GetRequestsTests extends BaseTest {

    /*
     * There should be a test case matching this test name in RUNMANAGER and TESTDATA sheet
     * If there are multiple data lines in TESTDATA sheet, it will treated as iteration
     * Mark Yes in RUNMANAGER and TESTDATA to run this test case
     * @author NVK PAVANKUMAR
     */

    @Test
    public void getActivityDetails(Hashtable<String, String> data, Method method) throws IOException {
        startTest(method.getName(), "Get Activity for ID : " + (int)Double.parseDouble(data.get("Id")));
        Response response = given()
                .filter(new RequestLoggingFilter(printStrObj))
                .log()
                .all()
                .get(Constants.BASEURL + Constants.GETACTIVITYBYID + (int)Double.parseDouble(data.get("Id")));
        Assert.assertEquals(response.getStatusCode(), 200);
        //TODO Assert.assertEquals (headers, )
        response.prettyPrint();
        /*
         * Writing the request and response to extent report
         *
         */
        writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());

        //Asserting the country code in the response using jsonPath.
        //Expected value is from TESTDATA sheet and column activity
        Assert.assertEquals(response.jsonPath().get("title"), data.get("expectedActivity"));

        //Writing the response to an log file
        Files.write(response.asByteArray(), new File(Constants.RESPONSETXTPATH + data.get("TestCaseName") + data.get("Id") + ".txt"));
    }
}