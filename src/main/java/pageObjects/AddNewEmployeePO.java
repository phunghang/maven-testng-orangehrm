package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.AddEmployeeUI;

public class AddNewEmployeePO extends BaseActions {
	private WebDriver driver;

	public AddNewEmployeePO(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public void enterToFirstnameTextbox(String firstName) {
		waitForElementVisible(driver, AddEmployeeUI.FIRST_NAME_TEXTBOX);
		sendkeyToElement(driver, AddEmployeeUI.FIRST_NAME_TEXTBOX, firstName);

	}

	public void enterToMiddlenameTextbox(String middleName) {
		waitForElementVisible(driver, AddEmployeeUI.MIDDLE_NAME_TEXTBOX);
		sendkeyToElement(driver, AddEmployeeUI.MIDDLE_NAME_TEXTBOX, middleName);

	}

	public void enterToLastnameTextbox(String lastName) {
		waitForElementVisible(driver, AddEmployeeUI.LAST_NAME_TEXTBOX);
		sendkeyToElement(driver, AddEmployeeUI.LAST_NAME_TEXTBOX, lastName);

	}

	public String getEmployeeId() {
		return getElementAtributeValue(driver, AddEmployeeUI.EMPLOYEE_ID_TEXTBOX, "value");
	}

	public void clickToSaveBt() {
		waitForElementClickable(driver, AddEmployeeUI.SAVE_BT);
		clickToElement(driver, AddEmployeeUI.SAVE_BT);
	}

	
}
