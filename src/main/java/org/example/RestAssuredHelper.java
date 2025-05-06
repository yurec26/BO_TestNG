package org.example;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class RestAssuredHelper {
    public static JsonNode sendRequestToJsonBins(
            Object body,
            String id,
            String method,
            Map<String, String> additionalHeaders,
            int status
    ) {
        Map<String, String> headers = new HashMap<>(Map.of("X-Master-Key",
                Objects.requireNonNull(ConfigHelper.getProperty("json_bin_token"))));

        headers.putAll(additionalHeaders);

        var request = given()
                .baseUri(Objects.requireNonNull(ConfigHelper.getProperty("base_jsonbin_url")))
                .basePath("/b%s".formatted(id))
                .headers(headers);
        if (body != null) {
            request.body(body);
        }
        return request
                .when()
                .request(method)
                .then().statusCode(status)
                .extract().body().as(JsonNode.class);
    }

}
