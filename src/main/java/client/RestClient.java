package client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestClient {
    public static Response postRequestResponse(String baseUrl,String path,String bearerToken,String body)
    {
        io.restassured.RestAssured.filters(new io.restassured.filter.log.RequestLoggingFilter(), new io.restassured.filter.log.ResponseLoggingFilter());
        Response response = given()
                .header("Authorization","Bearer "+bearerToken)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .baseUri(baseUrl)
                .body(body)
                .post(path);
        return  response;
    }
}
