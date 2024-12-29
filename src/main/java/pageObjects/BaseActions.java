package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.BasePage;
import pageUIs.AddEmployeeUI;
import pageUIs.BaseActionUIs;

public class BaseActions extends BasePage {
    private WebDriver driver;

    public BaseActions(WebDriver driver) {
        this.driver = driver;
    }

    public void waitIconLoadingInvisible() {
        waitForListElementInvisible(driver, BaseActionUIs.ICON_LOADING);
    }

    public boolean isSucessMsgDisplayed(String msg) {
        waitForElementVisible(driver, BaseActionUIs.DYNAMIC_SUCCESS_MSG, msg);
        return isElementDisplayed(driver, BaseActionUIs.DYNAMIC_SUCCESS_MSG, msg);
    }

    public boolean isValueDisplayedAtColumnName(String columnName, String rowIndex, String rowValue) {
        int columnIndex = getListElementsSize(driver, BaseActionUIs.DYNAMIC_COLUMN_INDEX_BY_COLUMN_NAME, columnName) + 1;
        return isElementDisplayed(driver, BaseActionUIs.DYNAMIC_COLUMN_VALUE_BY_ROW_INDEX_AND_COLUMN_INDEX, rowIndex, String.valueOf(columnIndex), rowValue);
    }

    public boolean isRequiredMsgDisplayed(String inputName) {
        waitForElementVisible(driver, BaseActionUIs.DYNAMIC_REQUIRED_MSG_BY_INPUT_NAME, inputName);
        return isElementDisplayed(driver, BaseActionUIs.DYNAMIC_REQUIRED_MSG_BY_INPUT_NAME, inputName);
    }

    public BaseActions openPage(String pageName) {
        waitForElementClickable(driver, BaseActionUIs.DYNAMIC_SIDE_BAR_BY_NAME, pageName);
        clickToElement(driver, BaseActionUIs.DYNAMIC_SIDE_BAR_BY_NAME, pageName);
        return switch (pageName) {
            case "PIM" -> new EmployeeListPO(driver);
            case "Dashboard" -> new DashboardPO(driver);
            default -> throw new RuntimeException("Không tồn tại page: " + pageName);
        };
    }


}
