import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

public class Data {
    static Faker faker = new Faker();
    public static String NAME = faker.name().firstName();
    public static String USER_EMAIL = NAME + "@mail.ru";
    public static String USER_PASSWORD = RandomStringUtils.randomAlphabetic(10);;
    public static String USER_NAME = NAME;
    public static List<String> INGREDIENTS = List.of("61c0c5a71d1f82001bdaaa79","61c0c5a71d1f82001bdaaa74");
    public static List<String> ERRORINGREDIENTS = List.of("","");
    public static List<String> NOINGREDIENTS = null;

}
