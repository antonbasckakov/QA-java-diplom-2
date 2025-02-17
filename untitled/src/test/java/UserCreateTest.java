import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class UserCreateTest {
    @Test
    @DisplayName("Создание нового юзера ")
    @Description("Проверка статус кода и текста ответа c ошибкой")
    public void createNewUser(){
        UserSteps userSteps = new UserSteps();
        UserPojo userPojo = new UserPojo(Data.USER_EMAIL,Data.USER_PASSWORD,Data.USER_NAME);
        Response response = userSteps.createNewUser(userPojo,Pens.USER_CREATE_POST);
        assertEquals(200,response.getStatusCode());
        assertEquals("true",response.jsonPath().getString("success"));
        assertEquals(Data.USER_EMAIL.toLowerCase(),response.jsonPath().getString("user.email").toLowerCase());
        assertEquals(Data.USER_NAME,response.jsonPath().getString("user.name"));
        assertThat(response.jsonPath().getString("accessToken"),notNullValue());
        assertThat(response.jsonPath().getString("refreshToken"),notNullValue());
        userSteps.deleteUser(response.jsonPath().getString("accessToken"),Pens.USER_REMOVAL_DELETE);

    }
    @Test
    @DisplayName("Создание нового юзера с данными зарегестрированно пользователя")
    @Description("Проверка статус кода и текста ответа c ошибкой")
    public void createDoubleUser(){
        UserSteps userSteps = new UserSteps();
        UserPojo userPojo = new UserPojo(Data.USER_EMAIL,Data.USER_PASSWORD,Data.USER_NAME);
        userSteps.createNewUser(userPojo,Pens.USER_CREATE_POST);
        Response response = userSteps.createNewUser(userPojo,Pens.USER_CREATE_POST);
        assertEquals(403,response.getStatusCode());
        assertEquals("false",response.jsonPath().getString("success").toLowerCase());
        assertEquals("User already exists",response.jsonPath().getString("message"));

    }

    @Test
    @DisplayName("Создание нового юзера без Email")
    @Description("Проверка статус кода и текста ответа c ошибкой")
    public void createUserWithoutEmail(){
        UserSteps userSteps = new UserSteps();
        UserPojo userPojo = new UserPojo(null,Data.USER_PASSWORD,Data.USER_NAME);
        Response response = userSteps.createNewUser(userPojo,Pens.USER_CREATE_POST);
        assertEquals(403,response.getStatusCode());
        assertEquals("false",response.jsonPath().getString("success").toLowerCase());
        assertEquals("Email, password and name are required fields",response.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Создание нового юзера без Email")
    @Description("Проверка статус кода и текста ответа c ошибкой")
    public void createUserWithoutName(){
        UserSteps userSteps = new UserSteps();
        UserPojo userPojo = new UserPojo(Data.USER_EMAIL,Data.USER_PASSWORD,null);
        Response response = userSteps.createNewUser(userPojo,Pens.USER_CREATE_POST);
        assertEquals(403,response.getStatusCode());
        assertEquals("false",response.jsonPath().getString("success").toLowerCase());
        assertEquals("Email, password and name are required fields",response.jsonPath().getString("message"));
    }
}
