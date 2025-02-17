import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetOrdersofUsers {
    @Test
    @DisplayName("Получение  заказа c авторизацией")
    @Description("Проверка статус кода и текста ответа ")
    public void getNewOrderWithToken(){
        UserSteps userSteps = new UserSteps();
        OrderSteps orderSteps = new OrderSteps();
        UserPojo userPojo = new UserPojo(Data.USER_EMAIL,Data.USER_PASSWORD,Data.USER_NAME);
        OrderPojo orderPojo = new OrderPojo(Data.INGREDIENTS);
        Response responseUser = userSteps.createNewUser(userPojo,Pens.USER_CREATE_POST);
        orderSteps.createNewOrder(responseUser.jsonPath().getString("accessToken"),orderPojo,Pens.ORDER_CREATE_POST);
        Response responseOrder = orderSteps.getOrderList(responseUser.jsonPath().getString("accessToken"),Pens.ORDER_CHECK_GET);
        assertEquals(200,responseOrder.getStatusCode());
        assertEquals("true",responseOrder.jsonPath().getString("success"));
        assertEquals("[Экзо-плантаго традиционный-галактический бургер]",responseOrder.jsonPath().getString("orders.name"));
        assertEquals(Data.FIRSTINGREDIENT,responseOrder.jsonPath().getString("orders.ingredients[0]"));
        assertEquals(Data.TWOINGREDIENT,responseOrder.jsonPath().getString("orders.ingredients[1]"));
        userSteps.deleteUser(responseUser.jsonPath().getString("accessToken"),Pens.USER_REMOVAL_DELETE);
    }

    @Test
    @DisplayName("Получение  заказа без авторизации")
    @Description("Проверка статус кода и текста ответа ")
    public void getNewOrderWithoutToken(){
        UserSteps userSteps = new UserSteps();
        OrderSteps orderSteps = new OrderSteps();
        UserPojo userPojo = new UserPojo(Data.USER_EMAIL,Data.USER_PASSWORD,Data.USER_NAME);
        OrderPojo orderPojo = new OrderPojo(Data.INGREDIENTS);
        Response responseUser = userSteps.createNewUser(userPojo,Pens.USER_CREATE_POST);
        orderSteps.createNewOrder(responseUser.jsonPath().getString("accessToken"),orderPojo,Pens.ORDER_CREATE_POST);
        Response responseOrder = orderSteps.getOrderList("",Pens.ORDER_CHECK_GET);
        assertEquals(401,responseOrder.getStatusCode());
        assertEquals("false",responseOrder.jsonPath().getString("success"));
        assertEquals("You should be authorised",responseOrder.jsonPath().getString("message"));
        userSteps.deleteUser(responseUser.jsonPath().getString("accessToken"),Pens.USER_REMOVAL_DELETE);
    }
}
