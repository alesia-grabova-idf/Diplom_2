import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

import clients.OrderClient;
import clients.UserClient;
import generators.OrderGenerator;
import generators.UserGenerator;
import io.restassured.response.Response;
import models.Order;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetOrderListTest {

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
  public void createOrderWithAuthorizationWithIngredientsTest() {
    Order order = OrderGenerator.generateRandomOrder();
    orderClient.createOrder(accessToken, order);
    Response usersOrdersResponse = orderClient.getUserOrders(accessToken);
    usersOrdersResponse.then()
        .assertThat()
        .statusCode(200)
        .body("success", is(true))
        .body("orders[0].number", notNullValue())
        .body("orders[0].ingredients", is(order.getIngredients()))
        .body("orders[0].number", notNullValue());
  }

  @Test
  public void createOrderWithoutAuthorizationWithIngredientsTest() {
    Order order = OrderGenerator.generateRandomOrder();
    orderClient.createOrder(accessToken, order);
    Response usersOrdersResponse = orderClient.getUserOrders("");
    usersOrdersResponse.then()
        .assertThat()
        .statusCode(401)
        .body("success", is(false))
        .body("message", is("You should be authorised"));
  }
}

