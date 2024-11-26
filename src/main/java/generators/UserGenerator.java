package generators;

import static utils.Utils.randomString;

import models.User;

public class UserGenerator {

  public static User randomUser() {
    final String EMAIL_DOMAIN = "@test.com";
    return new User(
        (randomString(8) + EMAIL_DOMAIN),
        (randomString(12)),
        (randomString(10))
    );
  }
}
