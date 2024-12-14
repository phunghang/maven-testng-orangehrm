package commons;

import groovy.json.JsonOutput;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class CommonActions {

    String getCookies (){
        String cookies = null;
        return cookies;
    }

    void createNewEmployeeByApi (String cookies, Map employeeInfo) {
        RestAssured.baseURI = "http://example.com"; // Thay URL API của bạn

        // Giả sử đây là cookie bạn lấy được từ web
        String sessionCookie = "JSESSIONID=abc123xyz";
        String requestBody = JsonOutput.toJson(employeeInfo);

        // Gọi API tạo nhân viên với cookie
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Cookie", cookies) // Gửi cookie trong header
                .body(requestBody)
                .when()
                .post("/api/employees") // Endpoint API
                .then()
                .statusCode(201)
                .extract()
                .response();
    }
}
