package pageObjects;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

// Put in elements that are found in the Add customer page
public class AddCustomerPage {

    public WebDriver ldriver; // local driver

    // Take driver from actual test and initiate PageFactory
    public AddCustomerPage(WebDriver rdriver) { // remote driver stored in local driver and accessible in entire class
        ldriver = rdriver;
        PageFactory.initElements(ldriver, this);
    }

    // Alternative way to locate web elements. Using locators and then an action method
    // In Tutorial Part 2, we used the @FindBy annotation instead. Refer to LoginPage Object

    // Step 1: Capture the locator of all elements
    By lnkCustomers_menu = By.xpath("//a[@href='#']//span[contains(text(), 'Customers')]");
    By getLnkCustomers_menuitem = By.xpath("//span[@class='menu-item-title'][contains(text(), 'Customers')]");

    By btnAddNew = By.xpath("//a[@class='btn bg-blue']");

    By txtEmail = By.xpath("//input[@id='Email']");
    By txtPassword = By.xpath("//input[@id='Password']");

    By txtcustomerRoles = By.xpath("//div[@class='k-multiselect-wrap k-floatwrap']");


    // Customer roles multi-select drop down
    By lstitemAdministrators = By.xpath("//li[contains(text(), 'Administrators')]");
    By lstitemRegistered = By.xpath("//li[contains(text(), 'Registered')]");
    By lstitemGuests = By.xpath("//li[contains(text(), 'Guests')]");
    By lstitemVendors = By.xpath("//li[contains(text(), 'Vendors')]");

    // Maanger of vendor drop down list
    By drpmgrOfVendor = By.xpath("//*[@id='VendorId']");
    By rdMaleGender = By.id("Gender_Male");
    By rdFeMaleGender = By.id("Gender_Female");

    By txtFirstName = By.xpath("//input[@id='FirstName']");
    By txtLastName = By.xpath("//input[@id='LastName']");

    By txtDob = By.xpath("//input[@id='DateOfBirth']");

    By txtCompanyName = By.xpath("//input[@id='Company']");

    By txtAdminContent = By.xpath("//textarea[@id='AdminComment']");

    By btnSave = By.xpath("//button[@name='save']");


    // Step 2: Action methods -- Using the

    public String getPageTitle() {
        return ldriver.getTitle();
    }

    public void clickOnCustomerMenu() {
        ldriver.findElement(lnkCustomers_menu).click();
    }

    public void clickOnCustomersMenuItem() {
        ldriver.findElement(getLnkCustomers_menuitem).click();
    }

    public void clickOnAddNew() {
        ldriver.findElement(btnAddNew).click();
    }

    public void setEmail(String email) {
        ldriver.findElement(txtEmail).sendKeys(email);
    }

    public void setPassword(String password) {
        ldriver.findElement(txtPassword).sendKeys(password);
    }

    public void setCustomerRoles(String role) throws InterruptedException {
        if (!role.equals("Vendors")) {// If role is vendors should not delete Registered
            ldriver.findElement(By.xpath("//*[@id=\"SelectedCustomerRoleIds_taglist\"]/li/span[2]")).click();
        }
        ldriver.findElement(txtcustomerRoles).click();

        WebElement listitem;

        Thread.sleep(3000);

        if (role.equals("Administrators")) {
            listitem = ldriver.findElement(lstitemAdministrators);
        } else if (role.equals("Guests")) {
            listitem = ldriver.findElement(lstitemGuests);
        } else if (role.equals("Registered")) {
            listitem = ldriver.findElement(lstitemRegistered);
        } else if (role.equals("Vendors")) {
            listitem = ldriver.findElement(lstitemVendors);
        } else {
            listitem = ldriver.findElement(lstitemGuests);
        }

        // listitem.click();
        // Thread.sleep(3000);

        // Alternative way for click action. Using Javascript executor method
        JavascriptExecutor js = (JavascriptExecutor)ldriver;
        js.executeScript("arguments[0].click();", listitem);

    }

    public void setManagerOfVendor(String value) {
        Select drp = new Select(ldriver.findElement((drpmgrOfVendor)));
        drp.selectByVisibleText(value);
    }

    public void setGender(String gender) {
        if (gender.equals("Male")) {
            ldriver.findElement(rdMaleGender).click();
        } else if (gender.equals("Female")) {
            ldriver.findElement(rdFeMaleGender).click();
        } else {
            ldriver.findElement(rdMaleGender).click(); // Default
        }
    }

    public void setFirstName(String fname) {
        ldriver.findElement(txtFirstName).sendKeys(fname);
    }

    public void setLastName(String lname) {
        ldriver.findElement(txtLastName).sendKeys(lname);
    }

    public void setDob(String dob) {
        ldriver.findElement(txtDob).sendKeys(dob);
    }

    public void setCompanyName(String comname) {
        ldriver.findElement(txtCompanyName).sendKeys(comname);
    }

    public void setAdminContent(String content){
        ldriver.findElement(txtAdminContent).sendKeys(content);
    }

    public void clickOnSave() {
        ldriver.findElement(btnSave).click();
    }

}
