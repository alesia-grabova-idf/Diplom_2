import static org.hamcrest.Matchers.is;

import clients.OrderClient;
import clients.UserClient;
import generators.OrderGenerator;
import generators.UserGenerator;
import io.restassured.response.Response;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import models.Order;

public class CreateOrderWithAuthorizationTest {

  private OrderClient orderClient;
  private String accessToken;
  private UserClient userClient;

  @Before
  public void setUp() {
    orderClient = new OrderClient();
    userClient = new UserClient();
    User user = UserGenerator.randomUser();
    Response registerResponse = userClient.registerUser(user);
    accessToken = userClient.extractToken(registerResponse);
  }

  @After
  public void tearDown() {
    if (accessToken != null) {
      userClient.deleteUser(accessToken);
    }
  }

  @Test
  public void createOrderWithAuthorizationWithoutIngredientsTest() {
    Order order = OrderGenerator.generateRandomOrder();
    order.setIngredients(null);
    Response madeOrderResponse = orderClient.createOrder(accessToken, order);
    madeOrderResponse.then()
        .assertThat()
        .statusCode(400)
        .body("success", is(false));
  }

  @Test
  public void createOrderWithAuthorizationWithIngredientsTest() {
    Order order = OrderGenerator.generateRandomOrder();
    Response madeOrderResponse = orderClient.createOrder(accessToken, order);
    madeOrderResponse.then()
        .assertThat()
        .statusCode(200)
        .body("success", is(true));
  }

  @Test
  public void createOrderWithAuthorizationWithInvalidIngredientsTest() {
    Order order = OrderGenerator.generateRandomOrder();
    order.setIngredients("ivalid123".lines().toList());
    Response madeOrderResponse = orderClient.createOrder(accessToken, order);
    madeOrderResponse.then()
        .assertThat()
        .statusCode(500);
  }
}
