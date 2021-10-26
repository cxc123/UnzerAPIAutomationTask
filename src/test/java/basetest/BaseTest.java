package basetest;

import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import static org.apache.logging.log4j.LogManager.*;

public class BaseTest {
    public static String bearerToken;
    public static String baseUrl;
    public static String emailId;
    public static String userName;
    public static String userId;
    public static String password;
    public static String sessionId;
    public final static Logger log = getLogger(BaseTest.class.getName());

    @BeforeSuite(alwaysRun = true)
    public  void setUp()
    {
        log.info("-------Setting up for running api test---------");
        baseUrl =System.getProperty("TEST_BASE_URL","https://api.m3o.com/v1/user");
        bearerToken=System.getProperty("BEARER_TOKEN","MTA2MzBlNjktZDI1Yy00MzNiLTkyMGQtNjNhNzU5YmM2NmE0");
        userId= utils.Utility.generateUserId();
        userName= utils.Utility.generateUsername(userId);
        emailId= utils.Utility.generateEmail(userId);
        password="mySecretPass1233";

    }
}
