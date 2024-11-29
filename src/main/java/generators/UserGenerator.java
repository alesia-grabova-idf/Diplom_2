package generators;

import com.github.javafaker.Faker;

import models.User;

public class UserGenerator {
  private static final Faker faker = new Faker();

  public static User randomUser() {
    return new User(
        faker.internet().emailAddress(), // Генерирует случайный email
        faker.internet().password(8, 16), // Генерирует случайный пароль от 8 до 16 символов
        faker.name().firstName() // Генерирует случайное имя
    );
  }
}
