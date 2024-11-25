import static org.hamcrest.Matchers.is;

import clients.OrderClient;
import generators.OrderGenerator;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import models.Order;

public class CreateOrderWithoutAuthorizationTest {

  private OrderClient orderClient;

  @Before
  public void setUp() {
    orderClient = new OrderClient();
  }


  @Test
  public void createOrderWithoutAuthorization() {
    Order order = OrderGenerator.generateRandomOrder();
    Response madeOrderResponse = orderClient.createOrder("", order);
    madeOrderResponse.then()
        .assertThat()
        .statusCode(200)
        .body("success", is(true));
  }

  @Test
  public void createOrderWithoutAuthorizationWithoutIngredients() {
    Order order = OrderGenerator.generateRandomOrder();
    order.setIngredients(null);
    Response madeOrderResponse = orderClient.createOrder("", order);
    madeOrderResponse.then()
        .assertThat()
        .statusCode(400)
        .body("success", is(false));
  }

  @Test
  public void createOrderWithoutAuthorizationWithInvalidIngredients() {
    Order order = OrderGenerator.generateRandomOrder();
    order.setIngredients("ivalid123".lines().toList());
    Response madeOrderResponse = orderClient.createOrder("", order);
    madeOrderResponse.then()
        .assertThat()
        .statusCode(500);
  }

}
