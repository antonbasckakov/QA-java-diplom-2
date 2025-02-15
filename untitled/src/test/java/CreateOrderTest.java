import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CreateOrderTest {

    @Test
    @DisplayName("Создание нового заказа c авторизацией")
    @Description("Проверка статус кода и текста ответа c ошибкой")
    public void createNewOrder(){
        UserSteps userSteps = new UserSteps();
        OrderSteps orderSteps = new OrderSteps();
        UserPojo userPojo = new UserPojo(Data.USER_EMAIL,Data.USER_PASSWORD,Data.USER_NAME);
        OrderPojo orderPojo = new OrderPojo(Data.INGREDIENTS);
        Response responseUser = userSteps.createNewUser(userPojo,Pens.USER_CREATE_POST);
        Response responseOrder = orderSteps.createNewOrder(responseUser.jsonPath().getString("accessToken"),orderPojo,Pens.ORDER_CREATE_POST);
        assertEquals(200,responseOrder.getStatusCode());
        assertEquals("true",responseOrder.jsonPath().getString("success"));
        assertEquals("Экзо-плантаго традиционный-галактический бургер",responseOrder.jsonPath().getString("name"));
        assertEquals("61c0c5a71d1f82001bdaaa79",responseOrder.jsonPath().getString("order.ingredients[0]._id"));
        assertEquals("61c0c5a71d1f82001bdaaa74",responseOrder.jsonPath().getString("order.ingredients[1]._id"));
        userSteps.deleteUser(responseUser.jsonPath().getString("accessToken"),Pens.USER_REMOVAL_DELETE);
    }

    @Test
    @DisplayName("Создание нового заказа без авторизации")
    @Description("Проверка статус кода и текста ответа c ошибкой")
    public void createNewOrderWithoutToken(){
        OrderSteps orderSteps = new OrderSteps();
        OrderPojo orderPojo = new OrderPojo(Data.INGREDIENTS);
        Response responseOrder = orderSteps.createNewOrder("",orderPojo,Pens.ORDER_CREATE_POST);
        assertEquals(200,responseOrder.getStatusCode());
        assertEquals("true",responseOrder.jsonPath().getString("success"));
        assertEquals("Экзо-плантаго традиционный-галактический бургер",responseOrder.jsonPath().getString("name"));
    }

    @Test
    @DisplayName("Создание нового заказа с неверным хешем ингредиентов")
    @Description("Проверка статус кода и текста ответа c ошибкой")
    public void createNewOrderWithoutIngredients(){
        OrderSteps orderSteps = new OrderSteps();
        OrderPojo orderPojo = new OrderPojo(Data.ERRORINGREDIENTS);
        Response responseOrder = orderSteps.createNewOrder("",orderPojo,Pens.ORDER_CREATE_POST);
        assertEquals(500,responseOrder.getStatusCode());

    }

    @Test
    @DisplayName("Создание нового заказа  без ингридиентов ")
    @Description("Проверка статус кода и текста ответа c ошибкой")
    public void createNewOrderWithErrorIngredients() {
        OrderSteps orderSteps = new OrderSteps();
        OrderPojo orderPojo = new OrderPojo(Data.NOINGREDIENTS);
        Response responseOrder = orderSteps.createNewOrder("", orderPojo, Pens.ORDER_CREATE_POST);
        assertEquals(400, responseOrder.getStatusCode());
        assertEquals("false", responseOrder.jsonPath().getString("success"));
        assertEquals("Ingredient ids must be provided", responseOrder.jsonPath().getString("message"));

    }
}
