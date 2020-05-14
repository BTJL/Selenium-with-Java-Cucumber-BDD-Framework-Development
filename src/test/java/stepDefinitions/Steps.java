package stepDefinitions;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import pageObjects.AddCustomerPage;
import pageObjects.LoginPage;
import pageObjects.SearchCustomerPage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Steps extends BaseClass {

    @Before // Cucumber hook to execute first before test cases start
    public void setup() throws IOException { // Contains all the configurations

        // Reading properties
        configProp = new Properties();
        FileInputStream configPropFile = new FileInputStream("config.properties"); // Opening the config file
        configProp.load(configPropFile);

        logger = Logger.getLogger("SeleniumCucumber2"); // Para - Name of project ; Added logger
        PropertyConfigurator.configure("log4j.properties"); // Para - specify the location of the file, if within project just the name

        // Launching the specified browser
        String browser = configProp.getProperty("browser");
        if (browser.equals("chrome")) { // equals value should match exactly with the value of browser in config.properties
            System.setProperty("webdriver.chrome.driver", configProp.getProperty("chromepath"));
            driver = new ChromeDriver();
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", configProp.getProperty("firefoxpath"));
            driver = new FirefoxDriver();
        } else if (browser.equals("ie")) {
            System.setProperty("webdriver.ie.driver", configProp.getProperty("iepath"));
            driver = new InternetExplorerDriver();
        }

        logger.info("****** Launching Browser ******");
    }


    @Given("User Launch Chrome browser")
    public void user_Launch_Chrome_browser() {
        lp = new LoginPage(driver);
    }

    @When("User opens URL {string}")
    public void user_opens_URL(String url) {
        logger.info("****** Opening URL ******");
        driver.get(url); // URL passed from feature file
//        driver.manage().window().maximize();
    }

    @When("User enters Email as {string} and Password as {string}")
    public void user_enters_Email_as_and_Password_as(String email, String password) {
        logger.info("****** Providing logging details ******");
        lp.setUsername(email);
        lp.setPassword(password);
    }

    @When("Click on Login")
    public void click_on_Login() {
        logger.info("****** Started login ******");
        lp.clickLogin();
    }

    // Method is used to satisfy two steps
    // Then Page Title should be "Your store. Login"
    // Then Page Title should be "Dashboard / nopCommerce administration"
    @Then("Page Title should be {string}")
    public void page_Title_should_be(String title) {

        // If wrong email or password is entered
        if (driver.getPageSource().contains("Login was unsuccessful")) {
            driver.close();
            logger.info("****** Login passed ******");
            Assert.assertTrue(false);
        } else {

            // If successful login, check the title
            logger.info("****** Login failed ******");
            Assert.assertEquals(title, driver.getTitle());
        }
    }

    @When("User click on Log out link")
    public void user_click_on_Log_out_link() throws InterruptedException {
        logger.info("****** Click on logout link ******");
        Thread.sleep(3000);
        lp.clickLogout();
        Thread.sleep(3000);
    }

    @Then("close browser")
    public void close_browser() {
        logger.info("****** Closing browser ******");
        driver.quit();
        // driver.close();
    }


    // Customer feature step definitions...............................................

    @Then("User can view Dashboard")
    public void user_can_view_Dashboard() {
        addCust = new AddCustomerPage(driver);
        Assert.assertEquals("Dashboard / nopCommerce administration", addCust.getPageTitle());
    }

    @When("User click on customers Menu")
    public void user_click_on_customers_Menu() throws InterruptedException {
        Thread.sleep(2000);
        addCust.clickOnCustomerMenu();
    }

    @When("click on customers Menu Item")
    public void click_on_customers_Menu_Item() throws InterruptedException {
        Thread.sleep(2000);
        addCust.clickOnCustomersMenuItem();
    }

    @When("click on Add new button")
    public void click_on_Add_new_button() throws InterruptedException {
        Thread.sleep(2000);
        addCust.clickOnAddNew();
    }

    @Then("User can view Add new customer page")
    public void user_can_view_Add_new_customer_page() {
        Assert.assertEquals("Add a new customer / nopCommerce administration", addCust.getPageTitle());
    };

    @When("User enter customer info")
    public void user_enter_customer_info() throws InterruptedException { // Setting of all relevant customer information in this method

        logger.info("****** Adding new customer ******");
        logger.info("****** Providing customer details ******");
        String email = randomstring() + "@gmail.com";
        addCust.setEmail(email);
        addCust.setPassword("test123");
        // Registered - default
        // The customer cannot be in both 'Guests' and 'Registered' customer roles
        // Add the customer to 'Guests' or 'Registered' customer role
        addCust.setCustomerRoles("Guest");
        Thread.sleep(3000);

        addCust.setManagerOfVendor("Vendor 2");
        addCust.setGender("Male");
        addCust.setFirstName("Pavan");
        addCust.setLastName("Kumar");
        addCust.setDob("7/05/1985"); // Format: D/MM/YYY
        addCust.setCompanyName("busyQA");
        addCust.setAdminContent("This is for testing.....");
    }

    @When("click on Save button")
    public void click_on_Save_button() throws InterruptedException {
        logger.info("****** Saving customer data ******");
        addCust.clickOnSave();
        Thread.sleep(2000);
    }

    @Then("User can view confirmation message {string}")
    public void user_can_view_confirmation_message(String msg) {
        Assert.assertTrue(driver.findElement(By.tagName("body")).getText()
                .contains("The new customer has been added successfully"));
    }


    // Steps for searching customer using Email ID ..............................................

    @When("Enter customer Email")
    public void enter_customer_Email() throws InterruptedException {
        Thread.sleep(2000);

        logger.info("****** Searching customer by using email id ******");
        searchCust = new SearchCustomerPage(driver);
        searchCust.setEmail("victoria_victoria@nopCommerce.com");
    }

    @When("Click on search button")
    public void click_on_search_button() throws InterruptedException {
        searchCust.clickSearch();
        Thread.sleep(3000);
    }

    @Then("User should found Email in the Search table")
    public void user_should_found_Email_in_the_Search_table() {
        boolean status = searchCust.searchCustomerByEmail("victoria_victoria@nopCommerce.com");
        Assert.assertEquals(true, status);
    }

    // Steps for searching a customer by using First Name & Last Name
    @When("Enter customer FirstName")
    public void enter_customer_FirstName() throws InterruptedException {
        Thread.sleep(2000);
        logger.info("****** Searching customer by Name ******");
        searchCust = new SearchCustomerPage(driver);
        searchCust.setEmail("Victoria");
    }

    @When("Enter customer LastName")
    public void enter_customer_LastName() {
        searchCust.setLastName("Terces");
    }

    @Then("User should found Name in the Search table")
    public void user_should_found_Name_in_the_Search_table() {
        boolean status = searchCust.searchCustomerByName("Victoria Terces");
        Assert.assertEquals(true, status);
    }
}
