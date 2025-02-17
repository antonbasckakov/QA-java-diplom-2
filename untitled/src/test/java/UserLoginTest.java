import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserLoginTest {
    @Test
    @DisplayName("Успешное логирование")
    @Description("Проверка статус кода и текста ответа ")
    public void loginUser(){
        UserSteps userSteps = new UserSteps();
        UserPojo userPojo = new UserPojo(Data.USER_EMAIL,Data.USER_PASSWORD,Data.USER_NAME);
        UserLogitPojo userLogitPojo = new UserLogitPojo(Data.USER_EMAIL,Data.USER_PASSWORD);
        userSteps.createNewUser(userPojo,Pens.USER_CREATE_POST);
        Response response = userSteps.loginNewUser(userLogitPojo,Pens.USER_AUTHORIZATION_POST);
        assertEquals(200,response.getStatusCode());
        assertEquals("true",response.jsonPath().getString("success").toLowerCase());
        userSteps.deleteUser(response.jsonPath().getString("accessToken"),Pens.USER_REMOVAL_DELETE);
    }

    @Test
    @DisplayName("Успешное логирование без пароля")
    @Description("Проверка статус кода и текста ответа ")
    public void loginUserWithoutPassword(){
        UserSteps userSteps = new UserSteps();
        UserPojo userPojo = new UserPojo(Data.USER_EMAIL,Data.USER_PASSWORD,Data.USER_NAME);
        UserLogitPojo userLogitPojo = new UserLogitPojo(Data.USER_EMAIL,null);
        userSteps.createNewUser(userPojo,Pens.USER_CREATE_POST);
        Response response = userSteps.loginNewUser(userLogitPojo,Pens.USER_AUTHORIZATION_POST);
        assertEquals(401,response.getStatusCode());
        assertEquals("false",response.jsonPath().getString("success"));
        assertEquals("email or password are incorrect",response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Успешное логирование без логина")
    @Description("Проверка статус кода и текста ответа ")
    public void loginUserWithoutEmail(){
        UserSteps userSteps = new UserSteps();
        UserPojo userPojo = new UserPojo(Data.USER_EMAIL,Data.USER_PASSWORD,Data.USER_NAME);
        UserLogitPojo userLogitPojo = new UserLogitPojo(null,Data.USER_PASSWORD);
        userSteps.createNewUser(userPojo,Pens.USER_CREATE_POST);
        Response response = userSteps.loginNewUser(userLogitPojo,Pens.USER_AUTHORIZATION_POST);
        assertEquals(401,response.getStatusCode());
        assertEquals("false",response.jsonPath().getString("success"));
        assertEquals("email or password are incorrect",response.jsonPath().getString("message"));
    }
}
