package clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import config.ConfigApp;

import static io.restassured.RestAssured.given;

public class OrderClient {

  @Step("Send POST request to create a new order")
  public Response createOrder(String accessToken, String[] ingredients) {
    return given()
        .header("Authorization", accessToken)
        .header("Content-Type", "application/json")
        .body("{\"ingredients\": " + toJsonArray(ingredients) + "}")
        .when()
        .post(ConfigApp.BASE_URL + ConfigApp.ORDER);
  }

  @Step("Send GET request to get user orders")
  public Response getUserOrders(String accessToken) {
    return given()
        .header("Authorization", accessToken)
        .header("Content-Type", "application/json")
        .when()
        .get(ConfigApp.BASE_URL + ConfigApp.ORDER);
  }

  @Step("Send GET request to get all orders")
  public Response getAllOrders() {
    return given()
        .header("Content-Type", "application/json")
        .when()
        .get(ConfigApp.BASE_URL + ConfigApp.GET_ALL_ORDERS);
  }

  private String toJsonArray(String[] items) {
    StringBuilder jsonArray = new StringBuilder("[");
    for (int i = 0; i < items.length; i++) {
      jsonArray.append("\"").append(items[i]).append("\"");
      if (i < items.length - 1) {
        jsonArray.append(",");
      }
    }
    jsonArray.append("]");
    return jsonArray.toString();
  }
}
