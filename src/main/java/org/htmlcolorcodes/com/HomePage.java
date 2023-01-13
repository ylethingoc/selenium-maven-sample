package org.htmlcolorcodes.com;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Loggers;
import utilities.WebDriverUtils;

public class HomePage extends WebDriverUtils {

    private WebDriver driver;

    @FindBy(xpath = "//a[@class='menu__link']/span[text()='Names']")
    @CacheLookup
    private WebElement namesDropdown;

    @FindBy(xpath = "//span[text()='HTML Color Names']")
    @CacheLookup
    private WebElement htmlDropdownItem;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //region <Action methods>
    public void selectHTMLColorNames() {
        Loggers.logInfo("Hover on Names dropdown");
        clickOnHTMLColorNames(driver, namesDropdown);

        Loggers.logInfo("Select HTML Color Name");
        htmlDropdownItem.click();
    }
    //endregion

    //region <Validation methods>
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    //endregion
}
