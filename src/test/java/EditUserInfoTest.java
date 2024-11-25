import clients.UserClient;
import io.restassured.response.Response;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static generators.UserGenerator.randomUser;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class EditUserInfoTest {
  private UserClient userClient;
  private User user;
  private String accessToken;

  @Before
  public void setUp(){
    userClient = new UserClient();
    user = randomUser();
    Response response = userClient.registerUser(user);
    accessToken = userClient.extractToken(response);
  }

  @After
  public void tearDown() {
    if (accessToken != null) {
      Response deleteResponse = userClient.deleteUser(accessToken);
      deleteResponse.then().statusCode(202);
    }
  }

  @Test
  public void testUpdateNameWithAuthorization() {
    user.setName("new_" + user.getName());
    userClient.updateUserInfo(accessToken, user)
        .then()
        .assertThat()
        .statusCode(200)
        .body("success", is(true))
        .body("user.name", equalTo(user.getName()));
  }

  @Test
  public void testUpdateEmailWithAuthorization() {
    user.setEmail("new_" + user.getEmail());
    userClient.updateUserInfo(accessToken, user)
        .then()
        .assertThat()
        .statusCode(200)
        .body("success", is(true))
        .body("user.email", equalTo(user.getEmail()));
  }

  @Test
  public void testUpdateUserWithoutAuthorization() {
    userClient.updateUserInfo("", user).then()
        .assertThat()
        .statusCode(401)
        .body("success", is(false));
  }
 }
