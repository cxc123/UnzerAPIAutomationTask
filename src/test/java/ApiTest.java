import client.RestClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Utility;

public class ApiTest {
    private String bearerToken;
    private String baseUrl;
    private static String emailId;
    private static String userName;
    private static String userId;
    private static String password;
    private static String sessionId;
    private final static Logger log = LogManager.getLogger(ApiTest.class.getName());


    @BeforeClass
    public void setUp()
    {
        log.info("-------Setting up for running api test---------");
        baseUrl =System.getProperty("TEST_BASE_URL","https://api.m3o.com/v1/user");
        bearerToken=System.getProperty("BEARER_TOKEN","MTA2MzBlNjktZDI1Yy00MzNiLTkyMGQtNjNhNzU5YmM2NmE0");
        userId=Utility.generateUserId();
        userName=Utility.generateUsername(userId);
        emailId=Utility.generateEmail(userId);
        password="mySecretPass1233";

    }

    @Test(groups = {"createuser"})
    public void testCreateUserWithMandatoryFields()
    {
        log.info("------running create user test with manadatory field-----------");
        Map<String,String> createUserAttribute= new java.util.HashMap<>();
        createUserAttribute.put("email",emailId);
        createUserAttribute.put("password",password);
        createUserAttribute.put("username",userName);
        createUserAttribute.put("id",userId);
        String createUserBody= Utility.generatePayload(createUserAttribute);
        Response createUserResponse= RestClient.postRequestResponse(baseUrl,"/Create",bearerToken,createUserBody);
    }

    @Test(groups = {"createuser"})
    public void testCreateUserWithoutMandatoryFields()
    {
        Map<String,String> createUserAttribute= new java.util.HashMap<>();
        createUserAttribute.put("password","mySecretPass1233");
        createUserAttribute.put("username","username1");
        String createUserBody= Utility.generatePayload(createUserAttribute);
        Response createUserResponse= RestClient.postRequestResponse(baseUrl,"/Create",bearerToken,createUserBody);
    }

    @Test(groups = {"getuser"},dependsOnMethods = {"testCreateUserWithMandatoryFields"})
    public void testExistGetUser()
    {
        log.info("------running get user test  for user which exist-----------");
        Map<String,String> getUserAttribute= new java.util.HashMap<>();
        getUserAttribute.put("id",userId);
        String getUserBody= Utility.generatePayload(getUserAttribute);
        Response createUserResponse= RestClient.postRequestResponse(baseUrl,"/Read",bearerToken,getUserBody);
    }

    @Test(groups = {"getuser"})
    public void testNotExistGetUser()
    {
        log.info("------running get user test  for user which not exist-----------");
        Map<String,String> getUserAttribute= new java.util.HashMap<>();
        getUserAttribute.put("id", UUID.randomUUID().toString());
        String getUserBody= Utility.generatePayload(getUserAttribute);
        Response createUserResponse= RestClient.postRequestResponse(baseUrl,"/Read",bearerToken,getUserBody);
    }

    @Test(groups = {"loginuser"},dependsOnMethods = {"testCreateUserWithMandatoryFields"})
    public void testLoginUserExistWithEmail()
    {
        log.info("------running login user test  for user which  exist-----------");
        Map<String,String> loginUserAttribute= new java.util.HashMap<>();
        loginUserAttribute.put("email",emailId);
        loginUserAttribute.put("password",password);
        String loginUserBody= Utility.generatePayload(loginUserAttribute);
        Response loginUserResponse= RestClient.postRequestResponse(baseUrl,"/Login",bearerToken,loginUserBody);
        sessionId="1";
    }

    @Test(groups = {"loginuser"},dependsOnMethods = {"testCreateUserWithMandatoryFields"})
    public void testLoginUserExistWithWrongCredentials()
    {
        log.info("------running login user test  for user which  exist-----------");
        Map<String,String> loginUserAttribute= new java.util.HashMap<>();
        loginUserAttribute.put("email",emailId);
        loginUserAttribute.put("password","000000000");
        String loginUserBody= Utility.generatePayload(loginUserAttribute);
        Response loginUserResponse= RestClient.postRequestResponse(baseUrl,"/Login",bearerToken,loginUserBody);
    }

    @Test(groups = {"logoutUser"},dependsOnGroups = {"loginuser"})
    public void testLogoutUser()
    {
        log.info("------running login user test  for user which  exist-----------");
        Map<String,String> logoutUserAttribute= new java.util.HashMap<>();
        logoutUserAttribute.put("sessionId",sessionId);
        String logoutUserBody= Utility.generatePayload(logoutUserAttribute);
        Response logoutUserResponse= RestClient.postRequestResponse(baseUrl,"/Logout",bearerToken,logoutUserBody);
    }
}
