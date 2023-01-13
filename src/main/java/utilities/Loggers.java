package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Loggers implements Constants {

    public static ExtentTest test;
    public static ExtentReports extent;
    public static String reportPath;
    public static ExtentSparkReporter spark;

    public static ExtentTest step;

    public void extentReportSpark() {

        reportPath = PROJECT_PATH + "/reports/" + getReportFolder() + "/";
        spark = new ExtentSparkReporter(reportPath + getReportFolder() + ".html");
        extent = new ExtentReports();
        extent.attachReporter(spark);

        try {
            spark.loadXMLConfig(PROJECT_PATH + "/src/main/resources/config.xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getReportFolder() {
        return "report-" + getCurrentTime();
    }

    private String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return sdf.format(date.getTime());
    }

    public void logStep(String stepDesc) {
        step = test.createNode(stepDesc);
    }

    public static void logInfo(String actionName) {
        step.info(actionName);
    }

    public static void logPass(String actionName) {
        step.pass(actionName);
    }

    public static void logFail(String actionName) throws Exception {
        step.log(Status.FAIL, actionName);
        throw new Exception();
    }
}
