package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.PersonnalDetailUI;

public class PersonnalDetailPO extends BaseActions {
	private WebDriver driver;

	public PersonnalDetailPO(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public String getFirstnameValue() {
		waitForElementVisible(driver, PersonnalDetailUI.FIRST_NAME_TEXTBOX);
		return getElementAtributeValue(driver, PersonnalDetailUI.FIRST_NAME_TEXTBOX, "value");
	}

	public String getMiddlenameValue() {
		waitForElementVisible(driver, PersonnalDetailUI.MIDDLE_NAME_TEXTBOX);
		return getElementAtributeValue(driver, PersonnalDetailUI.MIDDLE_NAME_TEXTBOX, "value");
	}

	public String getLastnameValue() {
		waitForElementVisible(driver, PersonnalDetailUI.LAST_NAME_TEXTBOX);
		return getElementAtributeValue(driver, PersonnalDetailUI.LAST_NAME_TEXTBOX, "value");
	}

	public String getEmployeeIdValue() {
		waitForElementVisible(driver, PersonnalDetailUI.EMPLOYEE_ID_TEXTBOX);
		return getElementAtributeValue(driver, PersonnalDetailUI.EMPLOYEE_ID_TEXTBOX, "value");
	}

	public EmployeeListPO clickToEmployeeListBt() {
		clickToElement(driver, PersonnalDetailUI.EMPLOYEE_LIST_BT);
		waitIconLoadingInvisible();
		return PageGeneratorManager.getEmployeeListPO(driver);
	}

}
