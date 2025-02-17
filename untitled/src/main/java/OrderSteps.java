import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderSteps extends BaseHttpClient {

    @Step("Создание нового заказа")
    public Response createNewOrder(String token,OrderPojo orderPojo, String path) {
        Response response = given()
                .spec(baseRequestSpec)
                .header("Authorization", token)
                .body(orderPojo)
                .when()
                .post(path);
        return response;
    }

    @Step("Получение списка заказов пользователя")
    public Response getOrderList(String token, String path) {
        Response response = given()
                .spec(baseRequestSpec)
                .header("authorization", token)
                .when()
                .get(path);
        return response;
    }

    @Step("Получение списка зингридиентов")
    public Response getOrderListAll(String path) {
        Response response = given()
                .spec(baseRequestSpec)
                .when()
                .get(path);
        return response;
    }
}
