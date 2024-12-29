package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.PageGeneratorManager;
import pageUIs.PersonalDetailUI;

public class PersonalDetailPO extends BaseActions {
    private WebDriver driver;

    public PersonalDetailPO(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public String getFirstnameValue() {
        waitForElementVisible(driver, PersonalDetailUI.FIRST_NAME_TEXTBOX);
        return getElementAtributeValue(driver, PersonalDetailUI.FIRST_NAME_TEXTBOX, "value");
    }

    public String getMiddlemenValue() {
        waitForElementVisible(driver, PersonalDetailUI.MIDDLE_NAME_TEXTBOX);
        return getElementAtributeValue(driver, PersonalDetailUI.MIDDLE_NAME_TEXTBOX, "value");
    }

    public String getLastnameValue() {
        waitForElementVisible(driver, PersonalDetailUI.LAST_NAME_TEXTBOX);
        return getElementAtributeValue(driver, PersonalDetailUI.LAST_NAME_TEXTBOX, "value");
    }

    public String getEmployeeIdValue() {
        waitForElementVisible(driver, PersonalDetailUI.EMPLOYEE_ID_TEXTBOX);
        return getElementAtributeValue(driver, PersonalDetailUI.EMPLOYEE_ID_TEXTBOX, "value");
    }

    public EmployeeListPO clickToEmployeeListBt() {
        clickToElement(driver, PersonalDetailUI.EMPLOYEE_LIST_BT);
        waitIconLoadingInvisible();
        return PageGeneratorManager.getEmployeeListPO(driver);
    }

    public void sendKeyToFirstnameTextbox(String firstnameValue) {
        waitForElementVisible(driver, PersonalDetailUI.FIRST_NAME_TEXTBOX);
        sendkeyToElement(driver, PersonalDetailUI.FIRST_NAME_TEXTBOX, firstnameValue);
    }

    public void sendKeyToLastnameTextbox(String lastnameValue) {
        waitForElementVisible(driver, PersonalDetailUI.LAST_NAME_TEXTBOX);
        sendkeyToElement(driver, PersonalDetailUI.LAST_NAME_TEXTBOX, lastnameValue);
    }

    public void sendKeyToMiddleNameTextbox(String middleNameValue) {
        waitForElementVisible(driver, PersonalDetailUI.MIDDLE_NAME_TEXTBOX);
        sendkeyToElement(driver, PersonalDetailUI.MIDDLE_NAME_TEXTBOX, middleNameValue);
    }

    public void clickToSaveBT() {
        waitForElementClickable(driver, PersonalDetailUI.SAVE_BT);
    }

}
