package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import commons.PageGeneratorManager;
import pageUIs.LoginUI;

public class LoginPO extends BaseActions {
	private WebDriver driver;

	public LoginPO(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public void enterToUsernameTextbox(String username) {
		waitForElementVisible(driver, LoginUI.USERNAME_TEXTBOX);
		sendkeyToElement(driver, LoginUI.USERNAME_TEXTBOX, username);
	}

	public void enterToPasswordTextbox(String password) {
		waitForElementVisible(driver, LoginUI.PASSWORD_TEXTBOX);
		sendkeyToElement(driver, LoginUI.PASSWORD_TEXTBOX, password);
	}

	public DashboardPO clickToLoginBt() {
		waitForElementClickable(driver, LoginUI.LOGIN_BT);
		clickToElement(driver, LoginUI.LOGIN_BT);
		waitIconLoadingInvisible();
		return PageGeneratorManager.getDashboardPO(driver);
	}

}
