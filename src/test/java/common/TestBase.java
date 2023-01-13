package common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utilities.Loggers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase extends Loggers {

    private static Properties prop;
    private static WebDriver driver;
    protected final Logger logger = LogManager.getLogger(getClass());

    public TestBase() {
        try {
            prop = new Properties();
            FileInputStream file = new FileInputStream(PROJECT_PATH + "/src/main/java/utilities/config.properties");
            prop.load(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeSuite
    public void setUp() {
        initialDriver();
        extentReportSpark();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        extent.flush();
        driver.close();
    }

    @AfterMethod
    public void captureScreenshot(ITestResult result) {

        String screenshotName = "error-" + getCurrentTime() + ".png";
        String screenshotLink = "<a href=\"" + screenshotName + "\">" + screenshotName + "</a>";
        String srcPath = reportPath + screenshotName;

        if (result.getStatus() == ITestResult.FAILURE) {
            try {

                // Take a screenshot and save it to a file
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                File src = screenshot.getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(src, new File(srcPath));

            } catch (Exception e) {
                e.printStackTrace();
            }

            step.fail(new Exception());
            // Add the screenshot to the test report
            step.fail(screenshotLink).addScreenCaptureFromPath(screenshotName);

        } else if (result.getStatus() == ITestResult.SKIP) {
            step.skip(new Throwable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            step.pass("PASSED!");
        }

    }

    private static void initialDriver() {
        String browserName = getObjectRepos("browser");

        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else if (browserName.equalsIgnoreCase("safari")) {
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
        }

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);

    }

    public static String getObjectRepos(String object) {
        return prop.getProperty(object);
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public void openURL(String url) {
        driver.get(url);
        logInfo("Navigate to: " + url);
    }

    private String getReportFolder() {
        String formattedTime = getCurrentTime();
        return "report-" + formattedTime;
    }

    private String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return sdf.format(date.getTime());
    }

}
