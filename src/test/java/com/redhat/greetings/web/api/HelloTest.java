package com.redhat.greetings.web.api;

import com.redhat.greetings.web.domain.GreetingJSON;
import com.redhat.greetings.web.infrastructure.GreetingService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.core.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class HelloTest {

    @InjectMock
    GreetingService greetingService;

    @BeforeEach
    public void setUp() {
        Mockito.when(greetingService.randomGreeting()).thenReturn(new GreetingJSON("Hidy Ho, Everybody!", "Mr. Hankey"));
    }

    @Test
    public void testHello() {
        given()
                .when().get("/hello")
                .then()
                .statusCode(200)
                .body(containsString("Hidy Ho, Everybody!"));

    }

}
