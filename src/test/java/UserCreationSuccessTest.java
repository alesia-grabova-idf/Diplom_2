import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import clients.UserClient;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import models.User;
import models.UserCreds;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserCreationSuccessTest {

  private UserClient userClient;
  private String accessToken;
  private static Faker faker = new Faker();

  @Before
  public void setUp() {
    userClient = new UserClient();
  }

  @After
  public void tearDown() {
    if (accessToken != null) {
      userClient.deleteUser(accessToken);
    }
  }

  @Test
  public void successfulUserRegistrationTest() {
    // Создаем пользователя с помощью Faker
    User user = new User(
        faker.internet().emailAddress(), // Генерация случайного email
        faker.internet().password(),    // Генерация случайного пароля
        faker.name().firstName()        // Генерация случайного имени
    );

    Response registerResponse = userClient.registerUser(user);
    // Проверка успешной регистрации
    registerResponse.then()
        .assertThat()
        .statusCode(200)
        .body("success", is(true))
        .body("user.email", equalTo(user.getEmail()))
        .body("user.name", equalTo(user.getName()));

    // Проверка авторизации пользователя
    Response loginResponse = userClient.loginUser(UserCreds.credsFromUser(user));
    loginResponse.then()
        .assertThat()
        .statusCode(200) // Проверка, что статус 200
        .body("success", is(true))
        .body("user.email", equalTo(user.getEmail()))
        .body("user.name", equalTo(user.getName()));
    accessToken = userClient.extractToken(loginResponse);
  }
}
