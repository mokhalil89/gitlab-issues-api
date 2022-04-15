package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestApiClient {

    Map<String, String> headers = new HashMap<String, String>() {{
        put("Authorization",
                "Bearer glpat-bzKmTsSL6ZQeXGSkfQRQ");
    }};
    RequestSpecification requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();


    public Response get(String url) {
        return given().headers(headers).spec(requestSpecification).get(url);
    }

    public Response post(String url, Object payload) {
        return given().headers(headers).spec(requestSpecification).body(payload).post(url);
    }

    public Response put(String url, Object payload) {
        return given().headers(headers).spec(requestSpecification).body(payload).put(url);
    }

    public Response delete(String url) {
        return given().headers(headers).spec(requestSpecification).delete(url);
    }
}