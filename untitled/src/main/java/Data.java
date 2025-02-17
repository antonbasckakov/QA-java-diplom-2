import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

public class Data {
    static OrderSteps orderSteps = new OrderSteps();
    static Response response = orderSteps.getOrderListAll(Pens.OLL_INGRIDIENTS);
    public  static String FIRSTINGREDIENT = response.jsonPath().getString("data[13]._id");
    public  static String TWOINGREDIENT = response.jsonPath().getString("data[7]._id");
    static Faker faker = new Faker();
    public static String NAME = faker.name().firstName();
    public static String USER_EMAIL = NAME + "@mail.ru";
    public static String USER_PASSWORD = RandomStringUtils.randomAlphabetic(10);;
    public static String USER_NAME = NAME;
    public static List<String> INGREDIENTS = List.of(FIRSTINGREDIENT,TWOINGREDIENT);
    public static List<String> ERRORINGREDIENTS = List.of("","");
    public static List<String> NOINGREDIENTS = null;

}
