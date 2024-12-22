package commons;

import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pageObjects.DashboardPO;
import pageObjects.LoginPO;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CommonActions {

    private static final Logger logger = LoggerFactory.getLogger(CommonActions.class);

    public static CommonActions getCommonActions() {
        return new CommonActions();
    }

    public DashboardPO login(WebDriver driver) {
        LoginPO loginPage;
        loginPage = PageGeneratorManager.getLoginPO(driver);
        loginPage.enterToUsernameTextbox(GlobalConstants.ADMIN_USER);
        loginPage.enterToPasswordTextbox(GlobalConstants.ADMIN_USER_PASSWORD);
        return loginPage.clickToLoginBt();
    }

    public String getCookies(WebDriver driver) {
        DashboardPO dashboardPage = PageGeneratorManager.getDashboardPO(driver);
        Set<Cookie> cookies = dashboardPage.getBrowserCookies(driver);
        String cookiesString = cookies.stream()
                .map(cookie -> cookie.getName() + "=" + cookie.getValue()) // Tạo chuỗi name=value
                .collect(Collectors.joining("; ")); // Ghép các cookie lại với "; "
        return cookiesString;

    }

    public void createNewEmployeeByApi(String apiBaseUrl, String cookies, Map<String, Object> employeeInfo) {
        logger.info("base url: {}", apiBaseUrl);
        logger.info("cokies: {}", cookies);
        logger.info("employee info {}", employeeInfo);
        RequestSpecification request = given()
                .header(new Header("Content-type", "application/json"))
                .header(new Header("Cookie", cookies))
                .baseUri(apiBaseUrl)
                .body(employeeInfo);

        Response response = request.post("/pim/employees");
        // Kiểm tra trạng thái phản hồi
        logger.info("Response Status Code: {}", response.getStatusCode());
        response.then().statusCode(equalTo(200));
    }
}
