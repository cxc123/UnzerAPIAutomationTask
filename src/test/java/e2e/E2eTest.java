package e2e;

import basetest.BaseTest;
import io.restassured.response.Response;
import model.request.AccountCreation;
import model.request.LoginUser;
import model.request.SessionId;
import model.request.UpdateUser;
import model.request.UserId;
import org.testng.annotations.Test;

import static client.RestClient.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class E2eTest extends BaseTest {

    @Test
    public void testEndToEndFlow()
    {
        log.info("------running create user test with manadatory field-----------");

        //creating user

        AccountCreation accountCreation=new AccountCreation();
        accountCreation.setEmail(emailId);
        accountCreation.setId(userId);
        accountCreation.setPassword(password);
        accountCreation.setUsername(userName);
        Response createUserResponse= postRequestResponse(baseUrl,"/Create",bearerToken,accountCreation);
        assertEquals(createUserResponse.getStatusCode(),200,"create user api not returned 200 http response");
        assertEquals(createUserResponse.then().extract().jsonPath().getString("account.id"),accountCreation.getId(),"userId returned is not matching");

        //read created user

        UserId id= new UserId();
        id.setId(userId);
        Response getUserResponse= postRequestResponse(baseUrl,"/Read",bearerToken,id);
        assertEquals(getUserResponse.getStatusCode(),200,"get user api not returned 200 http response");
        assertEquals(getUserResponse.then().extract().jsonPath().getString("account.id"),userId,"userId returned is not matching");
        assertEquals(getUserResponse.then().extract().jsonPath().getString("account.username"),userName,"userId returned is not matching");
        assertEquals(getUserResponse.then().extract().jsonPath().getString("account.email"),emailId,"userId returned is not matching");

        //Login with created user

        LoginUser loginUser=new LoginUser();
        loginUser.setEmail(emailId);
        loginUser.setPassword(password);
        Response loginUserResponse= postRequestResponse(baseUrl,"/Login",bearerToken,loginUser);
        assertEquals(loginUserResponse.getStatusCode(),200,"user login api not returned 200 http response");
        sessionId=loginUserResponse.then().extract().jsonPath().getString("session.id");
        assertNotNull(sessionId);

        //Logout user

        log.info("------running logout user test  for user which  exist-----------");
        SessionId loginSessionId=new SessionId();
        loginSessionId.setSessionId(sessionId);
        Response logoutUserResponse = postRequestResponse(baseUrl,"/Logout",bearerToken,loginSessionId);

        //Update user

        UpdateUser updateUser=new UpdateUser();
        updateUser.setEmail("update"+emailId);
        updateUser.setId(userId);
        updateUser.setUsername("update"+userName);
        Response updateUserResponse= postRequestResponse(baseUrl,"/Update",bearerToken,updateUser);
        assertEquals(updateUserResponse.getStatusCode(),200,"update user api not returned 200 http response");

        //Get user call to verify update is succesful

        id= new UserId();
        id.setId(userId);
        getUserResponse= postRequestResponse(baseUrl,"/Read",bearerToken,id);
        assertEquals(getUserResponse.getStatusCode(),200,"get user api not returned 200 http response");
        assertEquals(getUserResponse.then().extract().jsonPath().getString("account.id"),userId,"userId returned is not matching");
        assertEquals(getUserResponse.then().extract().jsonPath().getString("account.username"),"update"+userName,"userId returned is not matching");
        assertEquals(getUserResponse.then().extract().jsonPath().getString("account.email"),"update"+emailId,"userId returned is not matching");

        //Login user with update credentials

        loginUser=new LoginUser();
        loginUser.setEmail("update"+emailId);
        loginUser.setPassword(password);
        loginUserResponse= postRequestResponse(baseUrl,"/Login",bearerToken,loginUser);
        assertEquals(loginUserResponse.getStatusCode(),200,"user login api not returned 200 http response");
        sessionId=loginUserResponse.then().extract().jsonPath().getString("session.id");
        assertNotNull(sessionId);

        //Logout user
        log.info("------running logout user test  for user which  exist-----------");
        loginSessionId=new SessionId();
        loginSessionId.setSessionId(sessionId);
        logoutUserResponse = postRequestResponse(baseUrl,"/Logout",bearerToken,loginSessionId);
        assertEquals(logoutUserResponse.getStatusCode(),200,"user not able to logout");
    }
}
