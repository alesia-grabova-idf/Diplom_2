package generators;

import clients.OrderClient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.restassured.response.Response;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import models.Order;
import java.util.List;
import java.util.Random;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import java.util.ArrayList;

public class OrderGenerator {
  private static final Random RANDOM = new Random();

  private static List<String> getIngredientsFromApi() {
    Response ingredientsList = new OrderClient().getAllIngredients();
    String responseBody = ingredientsList.then()
        .extract()
        .body()
        .asString();
    JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
    JsonArray dataArray = jsonObject.getAsJsonArray("data");

    // Достаем "_id" поля из dataArray
    List<String> ids = new ArrayList<>();
    for (JsonElement item : dataArray) {
      ids.add(item.getAsJsonObject().get("_id").getAsString());
    }
    return ids;
  }

  public static Order generateRandomOrder() {
    // Случайное количество ингредиентов от 1 до 5
    int ingredientCount = RANDOM.nextInt(5) + 1;
    List<String> ingredientList = getIngredientsFromApi();

    // Выбор случайных ингредиентов из ingredientList
    List<String> randomIngredients = IntStream.range(0, ingredientCount)
        .mapToObj(i -> ingredientList.get(RANDOM.nextInt(ingredientList.size())))
        .collect(Collectors.toList());

    // Создание заказа
    return new Order(randomIngredients);
  }
}
