package orangehrm.pim;

import com.aventstack.extentreports.ExtentTest;
import commons.*;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.DashboardPO;
import pageObjects.EmployeeListPO;
import pageObjects.PersonalDetailPO;
import reportConfig.ExtentTestManager;
import utilities.FakerConfigs;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PIM_02_Personal_Details extends BaseTest {
    WebDriver driver;
    private DashboardPO dashboardPage;
    private EmployeeListPO employeeListPO;
    private PersonalDetailPO personalDetailPO;

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
        employeeId = fakerConfigs.getFirstName();
        lastname = fakerConfigs.getLastName();
        middelname = fakerConfigs.getMiddleName();
        commonActions = CommonActions.getCommonActions();
        dashboardPage = commonActions.login(driver);

        String cookies = commonActions.getCookies(driver);
        Map<String, Object> employeeInfo = new HashMap<>();
        employeeInfo.put("firstName", firstname);
        employeeInfo.put("lastName", lastname);
        employeeInfo.put("middleName", middelname);
        employeeInfo.put("employeeId", employeeId);
        commonActions.createNewEmployeeByApi(environment.getApiBaseUrl(), cookies, employeeInfo);

        employeeListPO = (EmployeeListPO) dashboardPage.openPage("PIM");
        personalDetailPO = employeeListPO.clickToPersonalDetailById(employeeId);
    }

    @Test
    void pd01_updateEmployeeInfoWithoutNonRequireInfo(Method method) {
        ExtentTest extentTest = ExtentTestManager.startTest(method.getName() + " Run on " + browserName, "pd01_updateEmployeeInfoWithoutNonRequireInfo");
        firstname = fakerConfigs.getFirstName();
        lastname = fakerConfigs.getLastName();
        extentTest.info("send key to firstname:  " + firstname);
        personalDetailPO.sendKeyToFirstnameTextbox(firstname);
        extentTest.info("send key to lastname:  " + lastname);
        personalDetailPO.sendKeyToLastnameTextbox(lastname);
        extentTest.info("send key to middle name with value empty" );
        personalDetailPO.sendKeyToMiddleNameTextbox("");
        personalDetailPO.clickToSaveBT();

        Assert.assertEquals(personalDetailPO.getFirstnameValue(), firstname);
        Assert.assertEquals(personalDetailPO.getMiddlemenValue(), "");
        Assert.assertEquals(personalDetailPO.getLastnameValue(), lastname);
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        closeBrowser();
    }

}
