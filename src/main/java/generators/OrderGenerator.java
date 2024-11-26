package generators;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import models.Order;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OrderGenerator {

  private static final List<String> INGREDIENTS_LIST = Arrays.asList(
      "61c0c5a71d1f82001bdaaa6d", // Флюоресцентная булка R2-D3
      "61c0c5a71d1f82001bdaaa6f", // Мясо бессмертных моллюсков Protostomia
      "61c0c5a71d1f82001bdaaa70", // Говяжий метеорит (отбивная)
      "61c0c5a71d1f82001bdaaa71", // Биокотлета из марсианской Магнолии
      "61c0c5a71d1f82001bdaaa72", // Соус Spicy-X
      "61c0c5a71d1f82001bdaaa6e", // Филе Люминесцентного тетраодонтимформа
      "61c0c5a71d1f82001bdaaa73", // Соус фирменный Space Sauce
      "61c0c5a71d1f82001bdaaa74", // Соус традиционный галактический
      "61c0c5a71d1f82001bdaaa6c", // Краторная булка N-200i
      "61c0c5a71d1f82001bdaaa75", // Соус с шипами Антарианского плоскоходца
      "61c0c5a71d1f82001bdaaa76", // Хрустящие минеральные кольца
      "61c0c5a71d1f82001bdaaa77", // Плоды Фалленианского дерева
      "61c0c5a71d1f82001bdaaa78", // Кристаллы марсианских альфа-сахаридов
      "61c0c5a71d1f82001bdaaa79", // Мини-салат Экзо-Плантаго
      "61c0c5a71d1f82001bdaaa7a", // Сыр с астероидной плесенью
      "61c0c5a71d1f82001bdaaa7b"  // Другие ингредиенты
  );

  private static final Random RANDOM = new Random();

  public static Order generateRandomOrder() {
    // Случайное количество ингредиентов от 1 до 5
    int ingredientCount = RANDOM.nextInt(5) + 1;

    // Выбор случайных ингредиентов из INGREDIENTS_LIST
    List<String> randomIngredients = IntStream.range(0, ingredientCount)
        .mapToObj(i -> INGREDIENTS_LIST.get(RANDOM.nextInt(INGREDIENTS_LIST.size())))
        .collect(Collectors.toList());

    // Создание заказа
    return new Order(randomIngredients);
  }
}
