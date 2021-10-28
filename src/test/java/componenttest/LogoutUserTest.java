package componenttest;

import client.RestClient;
import io.restassured.response.Response;
import model.request.SessionId;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static client.RestClient.postRequestResponse;
import static org.apache.logging.log4j.LogManager.getLogger;
import static org.testng.Assert.assertEquals;

public class LogoutUserTest extends basetest.BaseTest {

    private final static Logger log = getLogger(LogoutUserTest.class.getName());

    @Test(groups = {"logoutUser"},dependsOnGroups = {"loginuser"})
    public void testLogoutUser()
    {
        log.info("------running logout user test  for user which  exist-----------");
        SessionId id=new SessionId();
        id.setSessionId(sessionId);
        Response logoutUserResponse = postRequestResponse(baseUrl,"/Logout",bearerToken,id);
        assertEquals(logoutUserResponse.getStatusCode(),200,"user logout api not returned 500 http response");
    }
}
