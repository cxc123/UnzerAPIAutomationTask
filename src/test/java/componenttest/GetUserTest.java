package componenttest;

import basetest.BaseTest;
import client.RestClient;
import io.restassured.response.Response;
import model.request.UserId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static java.util.UUID.randomUUID;

import static client.RestClient.postRequestResponse;
import static org.testng.Assert.assertEquals;

public class GetUserTest extends BaseTest {

    private final static Logger log = LogManager.getLogger(componenttest.GetUserTest.class.getName());

    @Test(groups = {"getuser"},dependsOnGroups = {"createuser"})
    public void testExistGetUser()
    {
        log.info("------running get user test  for user which exist-----------");
        UserId id= new UserId();
        id.setId(userId);
        Response getUserResponse= postRequestResponse(baseUrl,"/Read",bearerToken,id);
        assertEquals(getUserResponse.getStatusCode(),200,"get user api not returned 200 http response");
        assertEquals(getUserResponse.then().extract().jsonPath().getString("account.id"),userId,"userId returned is not matching");
        assertEquals(getUserResponse.then().extract().jsonPath().getString("account.username"),userName,"userId returned is not matching");
        assertEquals(getUserResponse.then().extract().jsonPath().getString("account.email"),emailId,"userId returned is not matching");
    }

    @Test(groups = {"getuser"},dependsOnGroups = {"createuser"})
    public void testNotExistGetUser()
    {
        log.info("------running get user test  for user which not exist-----------");
        UserId id= new UserId();
        id.setId(randomUUID().toString());
        Response getUserResponse= postRequestResponse(baseUrl,"/Read",bearerToken,id);
        assertEquals(getUserResponse.getStatusCode(),500,"get user api not returned 500 http response");
    }

}
