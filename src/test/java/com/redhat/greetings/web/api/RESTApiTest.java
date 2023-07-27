package com.redhat.greetings.web.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.HttpHeaders;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.with;

@QuarkusTest
public class RESTApiTest {

    @Test
    public void testAddNewGreeting() {

        String json = """
                    {
                        "text":"Hello, OpenShift!",
                        "author":"Ozzy Osbourne"
                    }        
                """;

        with()
                .header(HttpHeaders.CONTENT_TYPE, ContentType.JSON)
                .body(json)
                .when()
                .request("POST", "/greetings")
                .then()
                .statusCode(202);
    }

    @Test
    public void testAllGreetings() {

    }



}
