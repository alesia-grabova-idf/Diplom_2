import static generators.UserGenerator.randomUser;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import clients.UserClient;
import io.restassured.response.Response;
import models.User;
import models.UserCreds;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;


public class LoginUserTest {
  private UserClient userClient;
  private User user;
  private String accessToken;

  @Before
  public void setUp(){
    userClient = new UserClient();
    user = randomUser();
    userClient.registerUser(user);

  }

  @After
  public void tearDown() {
    if (accessToken != null) {
      Response deleteResponse = userClient.deleteUser(accessToken);
      deleteResponse.then().statusCode(202);
    }
  }

  @Test
  @DisplayName("Successful Login")
  public void  successfulLogin(){
    Response loginResponse = userClient.loginUser(UserCreds.credsFromUser(user));
    loginResponse.then()
        .assertThat()
        .statusCode(200)
        .body("success", is(true))
        .body("user.email", equalTo(user.getEmail()))
        .body("user.name", equalTo(user.getName()));
    accessToken = userClient.extractToken(loginResponse);

  }

  @Test
  @DisplayName("Login with incorrect password")
  public void loginWithIncorrectPassword(){
    User wrongPasswordUser = new User(user.getEmail(), "wrongPassword", user.getName());
    UserCreds wrongPasswordCreds = UserCreds.credsFromUser(wrongPasswordUser);
    Response loginResponse = userClient.loginUser(wrongPasswordCreds);

    assertEquals("Неверный статус код при неправильном пароле", SC_UNAUTHORIZED, loginResponse.statusCode());
    assertTrue("Ожидается сообщение об ошибке", loginResponse.body().asString().contains("email or password are incorrect"));
  }

  @Test
  @DisplayName("Login with incorrect email")
  public void loginWithIncorrectEmail(){
    User wrongEmailUser = new User("wrongEmail", user.getPassword(), user.getName());
    UserCreds wrongEmailCreds = UserCreds.credsFromUser(wrongEmailUser);
    Response loginResponse = userClient.loginUser(wrongEmailCreds);

    assertEquals("Неверный статус код при неправильном емайле", SC_UNAUTHORIZED, loginResponse.statusCode());
    assertTrue("Ожидается сообщение об ошибке", loginResponse.body().asString().contains("email or password are incorrect"));
  }
}
