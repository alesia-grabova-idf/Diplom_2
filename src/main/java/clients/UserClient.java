package clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import config.ConfigApp;
import models.User;

import static io.restassured.RestAssured.given;

public class UserClient {

  @Step("Send POST request to register a new user")
  public Response registerUser(User user) {
    return given()
        .header("Content-Type", "application/json")
        .body(user)
        .when()
        .post(ConfigApp.BASE_URL + ConfigApp.REGISTER_USER);
  }

  @Step("Send POST request to login user")
  public Response loginUser(String email, String password) {
    return given()
        .header("Content-Type", "application/json")
        .body("{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}")
        .when()
        .post(ConfigApp.BASE_URL + ConfigApp.LOGIN_USER);
  }

  @Step("Send POST request to logout user")
  public Response logoutUser(String refreshToken) {
    return given()
        .header("Content-Type", "application/json")
        .body("{\"token\": \"" + refreshToken + "\"}")
        .when()
        .post(ConfigApp.BASE_URL + ConfigApp.LOGOUT_USER);
  }

  @Step("Send GET request to get user information")
  public Response getUserInfo(String accessToken) {
    return given()
        .header("Authorization", accessToken)
        .header("Content-Type", "application/json")
        .when()
        .get(ConfigApp.BASE_URL + ConfigApp.USER_INFO);
  }

  @Step("Send PATCH request to update user information")
  public Response updateUserInfo(String accessToken, User user) {
    return given()
        .header("Authorization", accessToken)
        .header("Content-Type", "application/json")
        .body(user)
        .when()
        .patch(ConfigApp.BASE_URL + ConfigApp.USER_INFO);
  }
}
