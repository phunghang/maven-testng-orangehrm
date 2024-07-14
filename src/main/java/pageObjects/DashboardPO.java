package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.DashboardUI;

public class DashboardPO extends BaseActions {
	private WebDriver driver;

	public DashboardPO(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public EmployeeListPO openEmployeeeList() {
		clickToElement(driver, DashboardUI.PIM_MODULE);
		waitIconLoadingInvisible();
		return PageGeneratorManager.getEmployeeListPO(driver);
	}

}
