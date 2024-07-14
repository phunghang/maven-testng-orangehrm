package reportConfig;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import commons.GlobalConstants;

public class ExtentManager {
	public static final ExtentReports extentReports = new ExtentReports();

	public synchronized static ExtentReports createExtentReports() {
		ExtentSparkReporter reporter = new ExtentSparkReporter(GlobalConstants.RELATIVE_PROJECT_PATH + "/extentReport/HTMLExtentReport5.html");
		reporter.config().setReportName("OrangeHRM HTML Report");
		reporter.config().setDocumentTitle("OrangeHRM HTML Report");
		reporter.config().setTimelineEnabled(true);
		reporter.config().setEncoding("utf-8");
		reporter.config().setTheme(Theme.STANDARD);

		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("Company", "Hang");
		extentReports.setSystemInfo("Project", "OrangeHRM");
		extentReports.setSystemInfo("Team", "Autotest");
		extentReports.setSystemInfo("JDK version", GlobalConstants.JAVA_VERSION);
		return extentReports;
	}
}