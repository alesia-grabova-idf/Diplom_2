package clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import config.ApiConfig;
import models.Order;

import static io.restassured.RestAssured.given;

public class OrderClient {

  @Step("Send POST request to create a new order")
  public Response createOrder(String accessToken, Order ingredients) {
    return given()
        .header("Authorization", accessToken)
        .header("Content-Type", "application/json")
        .body(ingredients)
        .when()
        .post(ApiConfig.BASE_URL + ApiConfig.ORDER);
  }

  @Step("Send GET request to get user orders")
  public Response getUserOrders(String accessToken) {
    return given()
        .header("Authorization", accessToken)
        .header("Content-Type", "application/json")
        .when()
        .get(ApiConfig.BASE_URL + ApiConfig.ORDER);
  }

  @Step("Send GET request to get all orders")
  public Response getAllOrders() {
    return given()
        .header("Content-Type", "application/json")
        .when()
        .get(ApiConfig.BASE_URL + ApiConfig.GET_ALL_ORDERS);
  }

  @Step("Send GET request to get all ingredients")
  public Response getAllIngredients() {
    return given()
        .header("Content-Type", "application/json")
        .when()
        .get(ApiConfig.BASE_URL + ApiConfig.GET_INGREDIENTS);
  }
}
