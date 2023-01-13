package org.htmlcolorcodes.com;

import common.TestBase;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC001_Validate_content_of_color_names extends TestBase {
    @Test(description = "[TC001] Validate content of color names")
    public void TC1() {
        test = extent.createTest("[TC001] Validate content of color names");

        logStep("Step #1: Goto " + BASE_URL);
        openURL(BASE_URL);

        logStep("Step #2: Validate current URL is '" + BASE_URL + "'");
        HomePage homePage = PageFactory.initElements(getDriver(), HomePage.class);
        Assert.assertEquals(homePage.getCurrentUrl(), BASE_URL);

        logStep("Step #3: Click on HTML Color Names");
        homePage.selectHTMLColorNames();
    }
}
