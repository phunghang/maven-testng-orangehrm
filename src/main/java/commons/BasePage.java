package commons;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

	// static: Không cần khởi tạo đối tượng mà vẫn có thể truy cập vào hàm được.
	// Truy cập trực tiếp từ phạm vi class
	public static BasePage getBasePage() {
		// Khởi tạo đối tượng
		return new BasePage();
	}

	/* Web browser */

	public void openPageUrl(WebDriver driver, String pageUrl) {
		driver.get(pageUrl);
	}

	public String getCurrentPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public String getCurrentPageUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	public String getCurrentPageSourceCode(WebDriver driver) {
		return driver.getPageSource();
	}

	public void backToPage(WebDriver driver) {
		driver.navigate().back();
	}

	public void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}

	public void refreshToPage(WebDriver driver) {
		driver.navigate().refresh();
	}

	public Alert waitAllertPresence(WebDriver driver) {
		return new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
				.until(ExpectedConditions.alertIsPresent());
	}

	public void acceptToAllert(WebDriver driver) {
		waitAllertPresence(driver).accept();
	}

	public void cancelToAllert(WebDriver driver) {
		waitAllertPresence(driver).dismiss();
	}

	public String getTextToAllert(WebDriver driver) {
		return waitAllertPresence(driver).getText();
	}

	public void sendkeyToAllert(WebDriver driver, String keysToSendkey) {
		waitAllertPresence(driver).sendKeys(keysToSendkey);
	}

	public void switchToWindowByID(WebDriver driver, String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindow : allWindows) {
			if (!runWindow.equals(parentID)) {
				driver.switchTo().window(runWindow);
				break;
			}
		}
	}

	public void switchToWindowByTitle(WebDriver driver, String title) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			driver.switchTo().window(runWindows);
			String currentWin = driver.getTitle();
			if (currentWin.equals(title)) {
				break;
			}
		}
	}

	public void closeAllWindowsWithoutParent(WebDriver driver, String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			if (!runWindows.equals(parentID)) {
				driver.switchTo().window(runWindows);
				driver.close();
			}
		}
		driver.switchTo().window(parentID);
	}

	public Set<Cookie> getBrowserCookies(WebDriver driver) {
		return driver.manage().getCookies();
	}

	public void setBrowserCookies(WebDriver driver, Set<Cookie> cookies) {
		for (Cookie cookie : cookies) {
			driver.manage().addCookie(cookie);
		}
	}

	public void deleteBrowserCookies(WebDriver driver) {
		driver.manage().deleteAllCookies();
	}

	/* Web element */

	public By getByLocator(String locator) {
		By by = null;
		if (locator.toLowerCase().startsWith("xpath=")) {
			by = By.xpath(locator.substring(6));
		} else if (locator.toLowerCase().startsWith("css=")) {
			by = By.cssSelector(locator.substring(4));
		} else if (locator.toLowerCase().startsWith("id=")) {
			by = By.id(locator.substring(3));
		} else if (locator.toLowerCase().startsWith("name=")) {
			by = By.name(locator.substring(5));
		} else if (locator.toLowerCase().startsWith("class=")) {
			by = By.className(locator.substring(6));
		} else if (locator.toLowerCase().startsWith("tagname=")) {
			by = By.tagName(locator.substring(8));
		} else {
			throw new RuntimeException("Locator type not found");
		}
		return by;
	}

	public By getByXpath(String locator) {
		return By.xpath(locator);
	}

	public String getDynamicLocator(String locator, String... dynamicValues) {
		return String.format(locator, (Object[]) dynamicValues);
	}

	public WebElement getWebElement(WebDriver driver, String locator) {
		return driver.findElement(getByLocator(locator));
	}

	public List<WebElement> getListWebElements(WebDriver driver, String locator) {
		return driver.findElements(getByLocator(locator));
	}

	/**
	 * list webelement với dynamic locator
	 * 
	 * @param driver
	 * @param locator
	 * @param dynamicValues
	 * @return
	 */
	public List<WebElement> getListWebElements(WebDriver driver, String locator, String... dynamicValues) {
		return driver.findElements(getByLocator(getDynamicLocator(locator, dynamicValues)));
	}

	public void clickToElement(WebDriver driver, String locator) {
		getWebElement(driver, locator).click();
	}

	public void clickToElement(WebDriver driver, WebElement element) {
		element.click();
	}

	/**
	 * click element với dynamic locator
	 * 
	 * @param driver
	 * @param locator
	 * @param dynamicValues
	 */
	public void clickToElement(WebDriver driver, String locator, String... dynamicValues) {
		getWebElement(driver, getDynamicLocator(locator, dynamicValues)).click();
	}

	/**
	 * Hàm này để sendkey
	 * 
	 * @param locator:     locator của element
	 * @param valueToSend: Giá trị sendkey
	 */
	public void sendkeyToElement(WebDriver driver, String locator, String valueToSend) {
		clearDataTextbox(driver, locator);
		getWebElement(driver, locator).sendKeys(valueToSend);
	}

	/**
	 * Senkey dynamic locator
	 * 
	 * @param driver
	 * @param locator
	 * @param valueToSend
	 */
	public void sendkeyToElement(WebDriver driver, String locator, String valueToSend, String... dynamicValues) {
		clearDataTextbox(driver, getDynamicLocator(locator, dynamicValues));
		getWebElement(driver, getDynamicLocator(locator, dynamicValues)).sendKeys(valueToSend);
	}

	public void clearDataTextbox(WebDriver driver, String locator) {
		getWebElement(driver, locator).clear();
	}

	/**
	 * Select dropdown theo giá trị truyền vào
	 * 
	 * @param dropdowLocation
	 * @param itemValue
	 */
	public void selectItemInDefaultDropdown(WebDriver driver, String dropdowLocation, String itemValue) {
		new Select(getWebElement(driver, dropdowLocation)).selectByVisibleText(itemValue);

	}

	public void selectItemInDefaultDropdown(WebDriver driver, String dropdownLocation, String itemValue,
			String... dynamicValues) {
		new Select(getWebElement(driver, getDynamicLocator(dropdownLocation, dynamicValues)))
				.selectByVisibleText(itemValue);

	}

	/**
	 * Lấy giá trị dropdown đang đọc chọn
	 * 
	 * @param dropdowLocation
	 * @return
	 */
	public String getSelectedTextItemInDefautlDropdown(WebDriver driver, String dropdowLocation) {
		return new Select(getWebElement(driver, dropdowLocation)).getFirstSelectedOption().getText();
	}

	/**
	 * Kiểm tra dropdown có phải là multiple
	 * 
	 * @param dropdowLocation
	 * @return
	 */
	public boolean isDropdownMultiple(WebDriver driver, String dropdowLocation) {
		return new Select(getWebElement(driver, dropdowLocation)).isMultiple();
	}

	/**
	 * Select giá trị trong custom dropdown
	 * 
	 * @param dropdowLocation
	 * @param itemLocator
	 * @param valueExpectedItem
	 */
	public void selectItemInCustomDropdown(WebDriver driver, String dropdowLocation, String itemLocator,
			String valueExpectedItem) {
		// Click vào dropdown để các item sổ xuống
		clickToElement(driver, dropdowLocation);
		;
		// Đợi cho tất cả các item của dropdown present
		new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByLocator(itemLocator)));
		// Khai báo list items của dropdown
		List<WebElement> dropListItems = getListWebElements(driver, itemLocator);
		// Duyệt tìm kiếm trong list items và click vào item thỏa mãn điều kiện
		for (WebElement item : dropListItems) {
			if (item.getText().trim().equals(valueExpectedItem)) {
				item.click();
				break;
			}
		}
	}

	/**
	 * Lấy ra giá trị atribute của element
	 * 
	 * @param locator
	 * @param atributeName
	 * @return
	 */
	public String getElementAtributeValue(WebDriver driver, String locator, String atributeName) {
		return getWebElement(driver, locator).getAttribute(atributeName);
	}
	
	public String getElementAtributeValue(WebDriver driver, String locator, String atributeName, String...dynamicValues ) {
		return getWebElement(driver, getDynamicLocator(locator, dynamicValues)).getAttribute(atributeName);
	}


	/**
	 * Lấy text của element
	 * 
	 * @param locator
	 * @return
	 */
	public String getTextElement(WebDriver driver, String locator) {
		return getWebElement(driver, locator).getText();
	}

	public String getTextElement(WebDriver driver, String locator, String... dynamicValues) {
		return getWebElement(driver, getDynamicLocator(locator, dynamicValues)).getText();
	}

	public String getCssValue(WebDriver driver, String locator, String propertyName) {
		return getWebElement(driver, locator).getCssValue(propertyName);
	}

	/**
	 * Lấy hexa color theo giá trị rgba truyền vào
	 * 
	 * @param rgbaValue
	 * @return
	 */
	public String getHexaColorByRgbaColor(String rgbaValue) {
		return Color.fromString(rgbaValue).asHex().toUpperCase();
	}

	public Integer getListElementsSize(WebDriver driver, String locator) {
		return getListWebElements(driver, locator).size();
	}

	public Integer getListElementsSize(WebDriver driver, String locator, String... dynamicValues) {
		return getListWebElements(driver, getDynamicLocator(locator, dynamicValues)).size();
	}

	/**
	 * Chọn radio BT/ chọn checkbox
	 * 
	 * @param locator
	 */
	public void checkToElement(WebDriver driver, String locator) {
		if (!getWebElement(driver, locator).isSelected()) {
			getWebElement(driver, locator).click();
		}
	}

	public void checkToElement(WebDriver driver, String locator, String... dynamicValues) {
		if (!getWebElement(driver, getDynamicLocator(locator, dynamicValues)).isSelected()) {
			getWebElement(driver, getDynamicLocator(locator, dynamicValues)).click();
		}
	}

	/**
	 * Bỏ chọn radio BT/ bỏ chọn checkbox
	 * 
	 * @param locator
	 */
	public void uncheckToElement(WebDriver driver, String locator) {
		if (getWebElement(driver, locator).isSelected()) {
			getWebElement(driver, locator).click();
		}
	}

	public boolean isElementSelected(WebDriver driver, String locator) {
		return getWebElement(driver, locator).isSelected();
	}

	public boolean isElementSelected(WebDriver driver, String locator, String... dynamicValues) {
		return getWebElement(driver, getDynamicLocator(locator, dynamicValues)).isSelected();
	}

	public boolean isElementDisplayed(WebDriver driver, String locator) {
		return getWebElement(driver, locator).isDisplayed();
	}

	public boolean isElementDisplayed(WebDriver driver, String locator, String... dynamicValues) {
		return getWebElement(driver, getDynamicLocator(locator, dynamicValues)).isDisplayed();
	}

	public boolean isElementUnDisplayed(WebDriver driver, String locator) {
		
		// Giảm thời gian chờ implict wait trong tìm kiếm element
		overrideGlobalTimeout(driver, GlobalConstants.SHORT_TIMEOUT);
		List<WebElement> elements = getListWebElements(driver, locator);
		// Set lại thời gian chờ
		overrideGlobalTimeout(driver, GlobalConstants.LONG_TIMEOUT);
		if (elements.size() == 0) {
			// element không có tron DOM
			return true;
		} else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
			// element có trong DOM nhưng không hiển thị trên UI
			return true;
		} else {
			// element displayed
			return false;
		}

	}

	public boolean isElementEnable(WebDriver driver, String locator) {
		return getWebElement(driver, locator).isEnabled();
	}

	public boolean isElementEnable(WebDriver driver, String locator, String... dynamicValues) {
		return getWebElement(driver, getDynamicLocator(locator, dynamicValues)).isEnabled();
	}

	/**
	 * switch to I frame/ frame
	 * 
	 * @param frameLocator
	 */
	public void switchToIframe(WebDriver driver, String frameLocator) {
		new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
				.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(getByLocator(frameLocator)));
	}

	/**
	 * switch về trang chứa frame/ iframe
	 */
	public void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	public void doubleClickToElement(WebDriver driver, String locator) {
		new Actions(driver).doubleClick(getWebElement(driver, locator)).perform();
	}

	public void moveToElement(WebDriver driver, String locator) {
		new Actions(driver).moveToElement(getWebElement(driver, locator)).perform();
	}

	public void rigtClickToElement(WebDriver driver, String locator) {
		new Actions(driver).contextClick(getWebElement(driver, locator)).perform();
	}

	public void dragAndDropElement(WebDriver driver, String sourceLocator, String targetLocator) {
		new Actions(driver).dragAndDrop(getWebElement(driver, sourceLocator), getWebElement(driver, targetLocator))
				.perform();
	}

	public void sendKeyboardToElement(WebDriver driver, String locator, Keys keyboard) {
		new Actions(driver).sendKeys(getWebElement(driver, locator), keyboard).perform();
	}

	/*
	 * Js excuter
	 */

	public Object executeForBrowser(WebDriver driver, String javaScript) {
		return ((JavascriptExecutor) driver).executeScript(javaScript);
	}

	public String getInnerText(WebDriver driver) {
		return (String) ((JavascriptExecutor) driver).executeScript("return document.documentElement.innerText;");
	}

	public boolean areExpectedTextInInnerText(WebDriver driver, String textExpected) {
		String textActual = (String) ((JavascriptExecutor) driver)
				.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(WebDriver driver, String url) {
		((JavascriptExecutor) driver).executeScript("window.location = '" + url + "'");
	}

	public void highlightElement(WebDriver driver, String locator) {
		WebElement element = getWebElement(driver, locator);
		String originalStyle = element.getAttribute("style");
		((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element,
				"style", "border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element,
				"style", originalStyle);
	}

	public void clickToElementByJS(WebDriver driver, String locator) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", getWebElement(driver, locator));
	}

	public void scrollToElement(WebDriver driver, String locator) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				getWebElement(driver, locator));
	}

	public void sendkeyToElementByJS(WebDriver driver, String locator, String value) {
		((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + value + "')",
				getWebElement(driver, locator));
	}

	public void removeAttributeInDOM(WebDriver driver, String locator, String attributeRemove) {
		((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('" + attributeRemove + "');",
				getWebElement(driver, locator));
	}

	public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}

	public String getElementValidationMessage(WebDriver driver, String locator) {
		return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;",
				getWebElement(driver, locator));
	}

	public boolean isImageLoaded(WebDriver driver, String locator) {
		boolean status = (boolean) ((JavascriptExecutor) driver).executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
				getWebElement(driver, locator));
		if (status) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Wait
	 */

	public void waitForElementVisible(WebDriver driver, String locator) {
		new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
				.until(ExpectedConditions.visibilityOfElementLocated(getByLocator(locator)));

	}

	public void waitForElementVisible(WebDriver driver, String locator, String... dynamicValues) {
		new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(
				ExpectedConditions.visibilityOfElementLocated(getByLocator(getDynamicLocator(locator, dynamicValues))));

	}

	public void waitForListElementVisible(WebDriver driver, String locator) {
		new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
				.until(ExpectedConditions.visibilityOfAllElements(getListWebElements(driver, locator)));
	}

	public void waitForListElementVisible(WebDriver driver, String locator, String... dynamicValues) {
		new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions
				.visibilityOfAllElements(getListWebElements(driver, getDynamicLocator(locator, dynamicValues))));
	}

	public void waitForElementInvisible(WebDriver driver, String locator) {
		new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
				.until(ExpectedConditions.invisibilityOfElementLocated(getByLocator(locator)));
	}

	public void waitForElementInvisible(WebDriver driver, String locator, String... dynamicValues) {
		new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions
				.invisibilityOfElementLocated(getByLocator(getDynamicLocator(locator, dynamicValues))));
	}

	public void waitForListElementInvisible(WebDriver driver, String locator) {
		new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
				.until(ExpectedConditions.invisibilityOfAllElements(getListWebElements(driver, locator)));
	}

	public void waitForListElementInvisible(WebDriver driver, String locator, String... dynamicValues) {
		new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(ExpectedConditions
				.invisibilityOfAllElements(getListWebElements(driver, getDynamicLocator(locator, dynamicValues))));
	}

	public void waitForElementClickable(WebDriver driver, String locator) {
		new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
				.until(ExpectedConditions.elementToBeClickable(getByLocator(locator)));
	}

	public void waitForElementClickable(WebDriver driver, WebElement element) {
		new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
				.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * Wait clickable dynamic locator
	 * 
	 * @param driver
	 * @param locator
	 * @param dynamicValues
	 */
	public void waitForElementClickable(WebDriver driver, String locator, String... dynamicValues) {
		new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT)).until(
				ExpectedConditions.elementToBeClickable(getByLocator(getDynamicLocator(locator, dynamicValues))));
	}

	public void waitForAllertPresent(WebDriver driver, String locator) {
		new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT))
				.until(ExpectedConditions.alertIsPresent());
	}

	public boolean isPageLoadedSuccess(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};
		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}

	public void uploadMultibleFiles(WebDriver driver,String locator, String... fileNames) {
		String filePath = GlobalConstants.UPLOAD_PATH;
		String fullFileName = "";
		for (String fileName : fileNames) {
			fullFileName = fullFileName + filePath + fileName + "\n";
		}
		fullFileName = fullFileName.trim();
		getWebElement(driver, locator).sendKeys(fullFileName);

	}

	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void overrideGlobalTimeout(WebDriver driver, long timeOut) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeOut));
	}

}
