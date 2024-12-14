package orangehrm.pim;

import java.lang.reflect.Method;

import commons.Environment;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import commons.GlobalConstants;
import commons.PageGeneratorManager;
import pageObjects.AddNewEmployeePO;
import pageObjects.DashboardPO;
import pageObjects.EmployeeListPO;
import pageObjects.LoginPO;
import pageObjects.PersonnalDetailPO;
import reportConfig.ExtentTestManager;
import utilities.FakerConfigs;

public class PIM_01_Employee_Create extends BaseTest {
    WebDriver driver;
    private String browserName, employeeId;
    private LoginPO loginPage;
    private DashboardPO dashboardPage;
    private EmployeeListPO employeeListPage;
    private AddNewEmployeePO addNewEmployeePage;
    private PersonnalDetailPO personnalDetailPage;
    private String firstname, middelname, lastname;
    private Environment environment;
    private FakerConfigs fakerConfigs;

    @Parameters({"browser"})
    @BeforeClass
    public void beforeClass(String browserName) {
        // Lấy environment từ cmd của maven (mvn clean test -DENV=dev):  -D+...: thể hiện biến trong cmd;

        String env = System.getProperty("ENV");
        ConfigFactory.setProperty("environment", env);
        // key trong setProperty này phải giống với biến trong @Sources tại Environment

        environment = ConfigFactory.create(Environment.class);
        driver = getWebDriver(browserName, environment.getUrl());
        this.browserName = browserName;
        fakerConfigs = FakerConfigs.getFaker();
        firstname = fakerConfigs.getFirstName();
        lastname = fakerConfigs.getLastName();
        middelname = fakerConfigs.getMiddleName();
        loginPage = PageGeneratorManager.getLoginPO(driver);
        loginPage.enterToUsernameTextbox(GlobalConstants.ADMIN_USER);
        loginPage.enterToPasswordTextbox(GlobalConstants.ADMIN_USER_PASSWORD);
        dashboardPage = loginPage.clickToLoginBt();
        employeeListPage = dashboardPage.openEmployeeeList();

    }

    @Test
    public void Employee_01_add_new_employee(Method method) {
        ExtentTestManager.startTest(method.getName() + " Run on " + browserName, "Employee_01_add_new_employee ");
        addNewEmployeePage = employeeListPage.clickToAddBt();
        addNewEmployeePage.enterToFirstnameTextbox(firstname);
        addNewEmployeePage.enterToMiddlenameTextbox(middelname);
        addNewEmployeePage.enterToLastnameTextbox(lastname);
        employeeId = addNewEmployeePage.getEmployeeId();
        addNewEmployeePage.clickToSaveBt();
        Assert.assertTrue(addNewEmployeePage.isSucessMsgDisplayed("Successfully Saved"));
        addNewEmployeePage.waitIconLoadingInvisible();

        personnalDetailPage = PageGeneratorManager.getPersonnalDetailPO(driver);
        personnalDetailPage.waitIconLoadingInvisible();
        Assert.assertEquals(personnalDetailPage.getFirstnameValue(), firstname);
        Assert.assertEquals(personnalDetailPage.getMiddlenameValue(), middelname);
        Assert.assertEquals(personnalDetailPage.getLastnameValue(), lastname);
        Assert.assertEquals(personnalDetailPage.getEmployeeIdValue(), employeeId);

        employeeListPage = personnalDetailPage.clickToEmployeeListBt();
        employeeListPage.enterToEmployeeIdSearchTextbox(employeeId);
        employeeListPage.clickToSearchBt();

        Assert.assertTrue(employeeListPage.isValueDisplayedAtColumnName("Id", "1", employeeId));
        Assert.assertTrue(employeeListPage.isValueDisplayedAtColumnName("First (& Middle) Name", "1",
                firstname + " " + middelname));
        Assert.assertTrue(employeeListPage.isValueDisplayedAtColumnName("Last Name", "1", lastname));

    }

    @Test
    public void Employee_01_add_new_employee_with_non_middle_name(Method method) {
        ExtentTestManager.startTest(method.getName() + " Run on " + browserName, "Employee_01_add_new_employee ");
        addNewEmployeePage = employeeListPage.clickToAddBt();
        addNewEmployeePage.enterToFirstnameTextbox(firstname);
        addNewEmployeePage.enterToLastnameTextbox(lastname);
        employeeId = addNewEmployeePage.getEmployeeId();
        addNewEmployeePage.clickToSaveBt();
        Assert.assertTrue(addNewEmployeePage.isSucessMsgDisplayed("Successfully Saved"));
        addNewEmployeePage.waitIconLoadingInvisible();

        personnalDetailPage = PageGeneratorManager.getPersonnalDetailPO(driver);
        personnalDetailPage.waitIconLoadingInvisible();
        Assert.assertEquals(personnalDetailPage.getFirstnameValue(), firstname);
        Assert.assertEquals(personnalDetailPage.getMiddlenameValue(), "");
        Assert.assertEquals(personnalDetailPage.getLastnameValue(), lastname);
        Assert.assertEquals(personnalDetailPage.getEmployeeIdValue(), employeeId);

        employeeListPage = personnalDetailPage.clickToEmployeeListBt();
        employeeListPage.enterToEmployeeIdSearchTextbox(employeeId);
        employeeListPage.clickToSearchBt();

        Assert.assertTrue(employeeListPage.isValueDisplayedAtColumnName("Id", "1", employeeId));
        Assert.assertTrue(employeeListPage.isValueDisplayedAtColumnName("First (& Middle) Name", "1", firstname));
        Assert.assertTrue(employeeListPage.isValueDisplayedAtColumnName("Last Name", "1", lastname));
    }

    @Test
    public void Employee_02_add_new_employee_with_empty_firstname(Method method) {
        ExtentTestManager.startTest(method.getName() + " Run on " + browserName, "Employee_01_add_new_employee ");
        addNewEmployeePage = employeeListPage.clickToAddBt();
        addNewEmployeePage.enterToMiddlenameTextbox(middelname);
        addNewEmployeePage.enterToLastnameTextbox(lastname);
        employeeId = addNewEmployeePage.getEmployeeId();
        addNewEmployeePage.clickToSaveBt();
        Assert.assertTrue(addNewEmployeePage.isRequiredMsgDisplayed("firstName"));
    }

    @Test
    public void Employee_03_add_new_employee_with_empty_lastname(Method method) {
        ExtentTestManager.startTest(method.getName() + " Run on " + browserName, "Employee_01_add_new_employee ");
        addNewEmployeePage = employeeListPage.clickToAddBt();
        addNewEmployeePage.enterToFirstnameTextbox(firstname);
        addNewEmployeePage.enterToMiddlenameTextbox(middelname);
        employeeId = addNewEmployeePage.getEmployeeId();
        addNewEmployeePage.clickToSaveBt();
        Assert.assertTrue(addNewEmployeePage.isRequiredMsgDisplayed("lastName"));
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        closeBrowser();
    }

}
