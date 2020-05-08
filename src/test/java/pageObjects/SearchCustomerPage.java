package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import utilities.WaitHelper;

import java.util.List;

public class SearchCustomerPage {

    public WebDriver ldriver;

    WaitHelper waitHelper;

    public SearchCustomerPage(WebDriver rdriver) {
        ldriver = rdriver;
        PageFactory.initElements(ldriver, this);
        waitHelper = new WaitHelper(ldriver);
    }

    @FindBy(how = How.ID, using = "SearchEmail")
    @CacheLookup
    WebElement txtEmail;

    @FindBy(how = How.ID, using = "SearchFirstName")
    @CacheLookup
    WebElement txtFirstName;

    @FindBy(how = How.ID, using = "SearchLastName")
    @CacheLookup
    WebElement txtLastName;


/*    Web elements for other validations if needed */
//    @FindBy(how = How.ID, using = "SearchMonthOfBirth")
//    @CacheLookup
//    WebElement drpdobMonth;
//
//    @FindBy(how = How.ID, using = "SearchDayOfBirth")
//    @CacheLookup
//    WebElement drpdobDay;
//
//    @FindBy(how = How.ID, using = "SearchCompany")
//    @CacheLookup
//    WebElement txtCompany;
//
//    @FindBy(how = How.XPATH, using = "//div[@class='k-multiselect-wrap k-floatwrap']")
//    @CacheLookup
//    WebElement txtCustomerRoles;
//
//    @FindBy(how = How.XPATH, using = "//li[contains(text(), 'Administrators')]")
//    @CacheLookup
//    WebElement lstitemAdministrators;
//
//    @FindBy(how = How.XPATH, using = "//li[contains(text(), 'Registered')]")
//    @CacheLookup
//    WebElement lstitemRegistered;
//
//    @FindBy(how = How.XPATH, using = "//li[contains(text(), 'Guests')]")
//    @CacheLookup
//    WebElement lstitemGuests;
//
//    @FindBy(how = How.XPATH, using = "//li[contains(text(), 'Vendors')]")
//    @CacheLookup
//    WebElement lstitemVendors;

    @FindBy(how = How.ID, using = "search-customers")
    @CacheLookup
    WebElement btnSearch;

    @FindBy(how = How.XPATH, using = "//table[@role='grid']")
    @CacheLookup
    WebElement tblSearchResults;

    @FindBy(how = How.XPATH, using = "//table[@id='customers-grid']")
    @CacheLookup
    WebElement table;

    @FindBy(how = How.XPATH, using = "//table[@id='customers-grid']//tbody/tr")  // Retrieves the number of Rows
    List<WebElement> tableRows;

    @FindBy(how = How.XPATH, using = "//table[@id='customers-grid']//tbody/tr/td") // Retrieves the number of Columns
    List<WebElement> tableColumns;

//    Action methods


    // First 3 action methods created for searching, which uses the Email, FirstName and LastName
    // If your form requires more methods then create accordingly
    public void setEmail(String email) {
        waitHelper.WaitForElement(txtEmail, 30); // Utilising the WaitHelper class in utilities that we created
        txtEmail.clear();
        txtEmail.sendKeys(email);
    }

    public void setFirstName(String fname) {
        waitHelper.WaitForElement(txtFirstName, 30);
        txtFirstName.clear();
        txtFirstName.sendKeys(fname);
    }

    public void setLastName(String lname) {
        waitHelper.WaitForElement(txtLastName, 30);
        txtLastName.clear();
        txtLastName.sendKeys(lname);
    }

    // Since the entered details above may produce multiple records, we need to read all the data in the table (rows + column data)
    // Check if records match exactly. Pass / Fail depending on whether it is found
    // Methods for searching the table
    public void clickSearch() {
        btnSearch.click();
        waitHelper.WaitForElement(btnSearch, 30);
    }

    public int getNoOfRows() { // Uses the .size() method from List class to obtain the number of Rows
        return tableRows.size();
    }

    public int getNoOfColumns() {
        return tableColumns.size();
    }


    // Search the email iteratively for every record in the table.
    public boolean searchCustomerByEmail(String email) {
        boolean flag = false;

        for (int i = 1; i <= getNoOfRows(); i++) {
            // Example of one cell Xpath //*[@id="customers-grid"]/tbody/tr[1]/td[2]
            String emailid = table.findElement(By.xpath("//table[@id='customers-grid']//tbody/tr[" + i + "]/td[2]")).getText();
            System.out.println(emailid);

            if (emailid.equals(email)) {
                flag = true;
            }
        }
        return flag;
    }




}
