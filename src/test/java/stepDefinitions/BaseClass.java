package stepDefinitions;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;

import pageObjects.AddCustomerPage;
import pageObjects.LoginPage;

public class BaseClass {

    // For all the steps these variables are required
    public WebDriver driver;
    public LoginPage lp;
    public AddCustomerPage addCust;

    // Created for generating random string for Unique email
    public static String randomstring() {
        return RandomStringUtils.randomAlphabetic(5);
    }

}

