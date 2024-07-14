package pageUIs;

public class BaseActionUIs {
	
	public static final String ICON_LOADING ="css=div.oxd-loading-spinner-container";
	public static final String DYNAMIC_SUCCESS_MSG = "xpath=//p[contains(@class,'oxd-text--toast-message') and text() ='%s']";
	public static final String DYNAMIC_COLUMN_INDEX_BY_COLUMN_NAME ="xpath=//div[@class='oxd-table-header']//div[text() ='%s']/preceding-sibling::div";
	public static final String DYNAMIC_COLUMN_VALUE_BY_ROW_INDEX_AND_COLUMN_INDEX ="xpath=//div[@class='oxd-table-card'][%s]//div[%s]/div[contains(text(),'%s')]";
	

}
