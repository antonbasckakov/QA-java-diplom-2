import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

public class UserSteps extends BaseHttpClient {

    @Step("Создание юзера")
    public Response createNewUser(UserPojo userPojo,String path) {
        Response response = given()
                .spec(baseRequestSpec)
                .body(userPojo)
                .when()
                .post(path);
        return response;
    }
    @Step("Логин юзера")
    public Response loginNewUser(UserLogitPojo userLogitPojo,String path) {
        Response response = given()
                .spec(baseRequestSpec)
                .body(userLogitPojo)
                .when()
                .post(path);
        return response;
    }

    @Step("Обновление юзера")
    public Response updateNewUser(String token,UserPojo userPojo,String path) {
        Response response = given()
                .spec(baseRequestSpec)
                .header("Authorization", token)
                .body(userPojo)
                .when()
                .patch(path);
        return response;
    }

    @Step("Удаление юзера")
    public Response deleteUser(String token ,String path){
        Response response = given()
                .spec(baseRequestSpec)
                .header("Authorization", token)
                .when()
                .delete(path);
        return response;

    }
}
