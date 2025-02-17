import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserUpdateTest {
    @Test
    @DisplayName("Успешное обновление Email")
    @Description("Проверка статус кода и текста ответа ")
    public void updateEmailUserwithToken(){
        UserSteps userSteps = new UserSteps();
        UserPojo userPojo = new UserPojo(Data.USER_EMAIL,Data.USER_PASSWORD,Data.USER_NAME);
        Response response = userSteps.createNewUser(userPojo,Pens.USER_CREATE_POST);
        String newAmail = "New" + Data.USER_EMAIL;
        UserPojo newUserPojo = new UserPojo(newAmail,Data.USER_PASSWORD,Data.USER_NAME);
        Response newResponse = userSteps.updateNewUser(response.jsonPath().getString("accessToken"),newUserPojo ,Pens.USER_UPDATE_PATCH);
        assertEquals(200,newResponse.getStatusCode());
        assertEquals("true",newResponse.jsonPath().getString("success").toLowerCase());
        assertEquals(newAmail.toLowerCase(),newResponse.jsonPath().getString("user.email"));
        userSteps.deleteUser(response.jsonPath().getString("accessToken"),Pens.USER_REMOVAL_DELETE);
    }

    @Test
    @DisplayName("Обновление Email без токена")
    @Description("Проверка статус кода и текста ответа ")
    public void updateEmailUserwithoutToken(){
        UserSteps userSteps = new UserSteps();
        UserPojo userPojo = new UserPojo(Data.USER_EMAIL,Data.USER_PASSWORD,Data.USER_NAME);
        String newAmail = "New" + Data.USER_EMAIL;
        UserPojo newUserPojo = new UserPojo(newAmail,Data.USER_PASSWORD,Data.USER_NAME);
        Response newResponse = userSteps.updateNewUser("",newUserPojo ,Pens.USER_UPDATE_PATCH);
        assertEquals(401,newResponse.getStatusCode());
        assertEquals("false",newResponse.jsonPath().getString("success").toLowerCase());
        assertEquals("You should be authorised",newResponse.jsonPath().getString("message"));
    }

    @Test
    @DisplayName("Успешное обновление Name")
    @Description("Проверка статус кода и текста ответа ")
    public void updateNameUserwithToken(){
        UserSteps userSteps = new UserSteps();
        UserPojo userPojo = new UserPojo(Data.USER_EMAIL,Data.USER_PASSWORD,Data.USER_NAME);
        Response response = userSteps.createNewUser(userPojo,Pens.USER_CREATE_POST);
        String newName = "New" + Data.USER_NAME;
        UserPojo newUserPojo = new UserPojo(Data.USER_EMAIL,Data.USER_PASSWORD,newName);
        Response newResponse = userSteps.updateNewUser(response.jsonPath().getString("accessToken"),newUserPojo ,Pens.USER_UPDATE_PATCH);
        assertEquals(200,newResponse.getStatusCode());
        assertEquals("true",newResponse.jsonPath().getString("success").toLowerCase());
        assertEquals(newName.toLowerCase(),newResponse.jsonPath().getString("user.name").toLowerCase());
        userSteps.deleteUser(response.jsonPath().getString("accessToken"),Pens.USER_REMOVAL_DELETE);

    }

    @Test
    @DisplayName("Обновление Name без токена")
    @Description("Проверка статус кода и текста ответа ")
    public void updateNameUserWithoutToken(){
        UserSteps userSteps = new UserSteps();
        UserPojo userPojo = new UserPojo(Data.USER_EMAIL,Data.USER_PASSWORD,Data.USER_NAME);
        String newName = "New" + Data.USER_NAME;
        UserPojo newUserPojo = new UserPojo(Data.USER_EMAIL,Data.USER_PASSWORD,newName);
        Response newResponse = userSteps.updateNewUser("",newUserPojo ,Pens.USER_UPDATE_PATCH);
        assertEquals(401,newResponse.getStatusCode());
        assertEquals("false",newResponse.jsonPath().getString("success").toLowerCase());
        assertEquals("You should be authorised",newResponse.jsonPath().getString("message"));
    }
}
