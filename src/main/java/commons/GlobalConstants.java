package commons;

import java.io.File;

public class GlobalConstants {

	public static final String DEV_ADMIN_URL = "http://localhost:90/orangehrm";
	public static final long LONG_TIMEOUT = 30;
	public static final long SHORT_TIMEOUT = 5;
	public static final String OS_NAME = System.getProperty("os.name");
	public static final String RELATIVE_PROJECT_PATH = System.getProperty("user.dir");
	public static final String JAVA_VERSION = System.getProperty("java.version");
	public static final String UPLOAD_PATH = RELATIVE_PROJECT_PATH + File.separator +  "uploadFiles" + File.separator;
	public static final String DOWNLOAD_PATH = RELATIVE_PROJECT_PATH + File.separator + "downloadFiles" + File.separator;
	public static final String ADMIN_USER = "hang_automationHR";
	public static final String ADMIN_USER_PASSWORD = "Hang95@***";
}
