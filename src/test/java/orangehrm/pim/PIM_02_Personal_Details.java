package orangehrm.pim;

import commons.*;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.DashboardPO;
import reportConfig.ExtentTestManager;
import utilities.FakerConfigs;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PIM_02_Personal_Details extends BaseTest {
    WebDriver driver;
    private DashboardPO dashboardPage;

    private Environment environment;
    private String browserName;
    private CommonActions commonActions;
    private FakerConfigs fakerConfigs;
    private String firstname, lastname, middelname, employeeId;

    @Parameters({"browser"})
    @BeforeClass
    public void beforeClass(String browserName) {

        String env = System.getProperty("ENV");
        ConfigFactory.setProperty("environment", env);
        environment = ConfigFactory.create(Environment.class);
        driver = getWebDriver(browserName, environment.getUrl());
        this.browserName = browserName;
        fakerConfigs = FakerConfigs.getFaker();
        firstname = fakerConfigs.getFirstName();
        lastname = fakerConfigs.getLastName();
        middelname = fakerConfigs.getMiddleName();
        commonActions = CommonActions.getCommonActions();
        dashboardPage = commonActions.login(driver);

        String cookies = commonActions.getCookies(driver);
        Map<String, Object> employeeInfo = new HashMap<>();
        employeeInfo.put("firstName", firstname);
        employeeInfo.put("lastName", lastname);
        employeeInfo.put("middleName", middelname);
        employeeInfo.put("employeeId", firstname);
        commonActions.createNewEmployeeByApi(environment.getApiBaseUrl(),cookies, employeeInfo);


    }

    @Test
    void test(Method method) {
        ExtentTestManager.startTest(method.getName() + " Run on " + browserName, "Employee_02_add_new_employee");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        closeBrowser();
    }

}
