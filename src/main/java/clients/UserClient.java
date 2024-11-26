package clients;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import config.ApiConfig;
import models.User;
import models.UserCreds;

import static io.restassured.RestAssured.given;

public class UserClient {

  @Step("Send POST request to register a new user")
  public Response registerUser(User user) {
    return given()
        .header("Content-Type", "application/json")
        .body(user)
        .when()
        .post(ApiConfig.BASE_URL + ApiConfig.REGISTER_USER);
  }

  @Step("Send POST request to login user")
  public Response loginUser(UserCreds userCreds) {
    return given()
        .header("Content-Type", "application/json")
        .body(userCreds)
        .when()
        .post(ApiConfig.BASE_URL + ApiConfig.LOGIN_USER);
  }

  @Step("Send POST request to logout user")
  public Response logoutUser(String refreshToken) {
    return given()
        .header("Content-Type", "application/json")
        .body("{\"token\": \"" + refreshToken + "\"}")
        .when()
        .post(ApiConfig.BASE_URL + ApiConfig.LOGOUT_USER);
  }

  @Step("Send GET request to get user information")
  public Response getUserInfo(String accessToken) {
    return given()
        .header("Authorization", accessToken)
        .header("Content-Type", "application/json")
        .when()
        .get(ApiConfig.BASE_URL + ApiConfig.USER_INFO);
  }

  @Step("Send PATCH request to update user information")
  public Response updateUserInfo(String accessToken, User user) {
    return given()
        .header("Authorization",  accessToken)
        .header("Content-Type", "application/json")
        .body(user)
        .when()
        .patch(ApiConfig.BASE_URL + ApiConfig.USER_INFO);
  }

  @Step("Send DELETE request to delete user")
  public Response deleteUser(String accessToken) {
    return given()
        .header("Authorization", accessToken)  // Используем токен для авторизации
        .contentType(ContentType.JSON)
        .when()
        .delete(ApiConfig.BASE_URL + ApiConfig.USER_INFO);
  }

  @Step("Extract access token from response")
  public String extractToken(Response response) {
    return response.jsonPath().getString("accessToken");
  }
}
