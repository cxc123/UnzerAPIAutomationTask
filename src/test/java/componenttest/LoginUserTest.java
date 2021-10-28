package componenttest;

import basetest.BaseTest;
import io.restassured.response.Response;
import model.request.LoginUser;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static client.RestClient.*;
import static org.apache.logging.log4j.LogManager.getLogger;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class LoginUserTest extends BaseTest {

    private final static Logger log = getLogger(LoginUserTest.class.getName());
    private LoginUser loginUser;

    @BeforeClass
    public void setUpLoginUser()
    {
         loginUser=new LoginUser();
         loginUser.setEmail(emailId);
         loginUser.setPassword(password);
    }

    @Test(groups = {"loginuser"},dependsOnGroups = {"getuser"},priority = 1)
    public void testLoginUserExistWithEmail()
    {
        Response loginUserResponse= postRequestResponse(baseUrl,"/Login",bearerToken,loginUser);
        assertEquals(loginUserResponse.getStatusCode(),200,"user login api not returned 200 http response");
        sessionId=loginUserResponse.then().extract().jsonPath().getString("session.id");
        assertNotNull(sessionId);
    }

    @Test(groups = {"loginuser"},dependsOnGroups = {"getuser"},priority = 2)
    public void testLoginUserExistWithWrongCredentials()
    {
        log.info("------running login user test  for user with wrong password-----------");
        loginUser.setPassword("00000000000");
        Response loginUserResponse= postRequestResponse(baseUrl,"/Login",bearerToken,loginUser);
        assertEquals(loginUserResponse.getStatusCode(),401,"user login api not returned 500 http response");
        assertEquals(loginUserResponse.then().extract().jsonPath().getString("Status"),"Unauthorized","user able to login with wrong credentials");
    }

}
