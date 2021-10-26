package componenttest;

import basetest.BaseTest;
import client.RestClient;
import io.restassured.response.Response;
import model.request.AccountCreation;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.apache.logging.log4j.LogManager.getLogger;
import static org.testng.Assert.assertEquals;

public class CreateUserTest extends BaseTest {

    private final static Logger log = getLogger(CreateUserTest.class.getName());
    private AccountCreation accountCreation;

    @BeforeClass public void setUpCreateUser()
    {
        accountCreation=new AccountCreation();
        accountCreation.setEmail(emailId);
        accountCreation.setId(userId);
        accountCreation.setPassword(password);
        accountCreation.setUsername(userName);
    }

    @Test(groups = {"createuser"},priority = 1)
    public void testCreateUserWithMandatoryFields()
    {
        log.info("------running create user test with manadatory field-----------");
        Response createUserResponse= RestClient.postRequestResponse(baseUrl,"/Create",bearerToken,accountCreation);
        assertEquals(createUserResponse.getStatusCode(),200,"create user api not returned 200 http response");
        assertEquals(createUserResponse.then().extract().jsonPath().getString("account.id"),userId,"userId returned is not matching");
    }

    @Test(groups = {"createuser"},priority = 2)
    public void testCreateUserWithoutMandatoryFields()
    {
        log.info("------running create user test with missing email id-----------");
        accountCreation.setEmail(null);
        Response createUserResponse= RestClient.postRequestResponse(baseUrl,"/Create",bearerToken,accountCreation);
        assertEquals(createUserResponse.getStatusCode(),400,"user is created without mandatory fields");
    }
}
