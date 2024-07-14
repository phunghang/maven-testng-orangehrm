package commons;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseTest {
	private WebDriver driver;

	public WebDriver getDriver() {
		return driver;
	}

	protected WebDriver getWebDriver(String driverName, String url) {

		BrowserList browser = BrowserList.valueOf(driverName.toUpperCase());

		switch (browser) {
		case CHROME:
			driver = new ChromeDriver();
			break;
		case FIREFOX:
			driver = new FirefoxDriver();
			break;
		case EDGE:
			driver = new EdgeDriver();
			break;
		default:
			throw new RuntimeException("Chưa có browser: " + browser);
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalConstants.LONG_TIMEOUT));
		driver.manage().window().maximize();
		driver.get(url);
		return driver;

	}

	protected void closeBrowser() {
		String cmd = null;
		try {
			String osName = GlobalConstants.OS_NAME.toLowerCase();

			String driverInstanceName = driver.toString().toLowerCase();

			String browserDriverName = null;

			if (driverInstanceName.contains("chrome")) {
				browserDriverName = "chromedriver";
			} else if (driverInstanceName.contains("internetexplorer")) {
				browserDriverName = "IEDriverServer";
			} else if (driverInstanceName.contains("firefox")) {
				browserDriverName = "geckodriver";
			} else if (driverInstanceName.contains("edge")) {
				browserDriverName = "msedgedriver";
			} else if (driverInstanceName.contains("opera")) {
				browserDriverName = "operadriver";
			} else {
				browserDriverName = "safaridriver";
			}

			if (osName.contains("window")) {
				// câu lệnh đóng task của cmd trên window
				cmd = "taskkill /F /FI \"IMAGENAME eq " + browserDriverName + "*\"";
			} else {
				// câu lệnh đóng task của cmd trên mac, linux
				cmd = "pkill " + browserDriverName;
			}

			// 1. Đóng trình duyệt web
			if (driver != null) {
				// deleteAllCookies dùng cho IED
				// driver.manage().deleteAllCookies();
				driver.quit();
			}
		} catch (Exception e) {

		} finally {
			try {
				// Thực thi câu lệnh cmd
				Process process = Runtime.getRuntime().exec(cmd);
				process.waitFor();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
