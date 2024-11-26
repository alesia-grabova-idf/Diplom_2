import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static utils.Utils.randomString;

import clients.UserClient;
import io.restassured.response.Response;
import models.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.assertj.core.api.SoftAssertions;

@RunWith(Parameterized.class)
public class UserCreationNegativeTest {

  private UserClient userClient;
  private User user;
  private int expectedStatusCode;
  private String expectedError;
  private String accessToken;

  private static String EXISTING_EMAIL = randomString(8) + "@test.com";

  public UserCreationNegativeTest(String testName, User user, int expectedStatusCode, String expectedError) {
    this.user = user;
    this.expectedStatusCode = expectedStatusCode;
    this.expectedError = expectedError;
  }

  @Parameterized.Parameters(name = "{index}: {0}")
  public static Object[][] testData() {
    return new Object[][]{
        {"Without email field", new User(null, randomString(12), randomString(10)),
            SC_FORBIDDEN, "Email, password and name are required fields"},
        {"Without password field", new User(randomString(8), null, randomString(10)),
            SC_FORBIDDEN, "Email, password and name are required fields"},
        {"Without name field", new User(randomString(8), randomString(12), null),
            SC_FORBIDDEN, "Email, password and name are required fields"},
        {"Create user with existing email",
            new User(EXISTING_EMAIL, randomString(12), randomString(10)),
            SC_FORBIDDEN, "User already exists"}
    };
  }

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
  public void testUserCreationNegativeCases() {
    // Если тест на дублирующегося пользователя, сначала регистрируем его
    if (expectedStatusCode == SC_FORBIDDEN && expectedError.equals("User already exists")) {
      // Регистрируем пользователя с этим email
      Response registerResponse = userClient.registerUser(user);
      accessToken = userClient.extractToken(registerResponse);
    }
    Response response = userClient.registerUser(user);

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(response.statusCode())
        .as("Неверный статус код для " + expectedError)
        .isEqualTo(expectedStatusCode);

    softAssertions.assertThat(response.body().asString())
        .as("Ожидается сообщение об ошибке: " + expectedError)
        .contains(expectedError);

    softAssertions.assertAll();
  }
}
