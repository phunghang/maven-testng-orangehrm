package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.EmployeeListUI;

public class EmployeeListPO extends BaseActions{
	private WebDriver driver;

	public EmployeeListPO(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public AddNewEmployeePO clickToAddBt() {
		clickToElement(driver, EmployeeListUI.ADD_EMPLOYEE_BT);
		waitIconLoadingInvisible();
		return PageGeneratorManager.getAddEmployeePO(driver);
	}

	public void enterToEmployeeIdSearchTextbox(String employeeId) {
		waitForElementVisible(driver, EmployeeListUI.EMPLOYEE_ID_TEXTBOX);
		sendkeyToElement(driver, EmployeeListUI.EMPLOYEE_ID_TEXTBOX, employeeId);
		
	}

	public void clickToSearchBt() {
		waitForElementClickable(driver, EmployeeListUI.SEARCH_BT);
		clickToElement(driver, EmployeeListUI.SEARCH_BT);
		waitIconLoadingInvisible();
		
	}

}
