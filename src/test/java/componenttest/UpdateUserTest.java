package componenttest;

import client.RestClient;
import io.restassured.response.Response;
import java.util.UUID;
import model.request.UpdateUser;
import model.request.UserId;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static client.RestClient.*;
import static org.apache.logging.log4j.LogManager.getLogger;
import static org.testng.Assert.assertEquals;

public class UpdateUserTest extends basetest.BaseTest {

    private final static Logger log = getLogger(UpdateUserTest.class.getName());

    @Test(dependsOnGroups = {"getuser","loginuser"},priority = 1)
    public void updateExistUserTest()
    {

        UpdateUser updateUser=new UpdateUser();
        updateUser.setEmail("update"+emailId);
        updateUser.setId(userId);
        updateUser.setUsername("update"+userName);
        Response updateUserResponse= postRequestResponse(baseUrl,"/Update",bearerToken,updateUser);
        assertEquals(updateUserResponse.getStatusCode(),200,"update is not successful");
        UserId id= new UserId();
        id.setId(userId);
        Response getuserUpdateResponse= postRequestResponse(baseUrl,"/Read",bearerToken,id);
        assertEquals(getuserUpdateResponse.then().extract().jsonPath().getString("account.id"),userId,"userId returned is not matching");
        assertEquals(getuserUpdateResponse.then().extract().jsonPath().getString("account.username"),"update"+userName,"userId returned is not matching");
        assertEquals(getuserUpdateResponse.then().extract().jsonPath().getString("account.email"),"update"+emailId,"userId returned is not matching");

    }

    @Test(dependsOnGroups = {"getuser","loginuser"},priority = 2)
    public void updateNotExistUserTest()
    {

        UpdateUser updateUser=new UpdateUser();
        updateUser.setEmail("update"+emailId);
        updateUser.setId(UUID.randomUUID().toString());
        updateUser.setUsername("update"+userName);
        Response getUserResponse= postRequestResponse(baseUrl,"/Update",bearerToken,updateUser);
        assertEquals(getUserResponse.getStatusCode(),500,"update user api not returned 500 http response");


    }

}
