package com.redhat.greetings.web.infrastructure;

import com.redhat.greetings.web.domain.GreetingJSON;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class GreetingResourceTest {

    static final Logger LOGGER = LoggerFactory.getLogger(GreetingResourceTest.class);

    @InjectMock
    GreetingService greetingService;

    String text = "Hi, there JaCoCo!";
    String author = "Lemmy Kilminster";


    @BeforeEach
    void setUp() {
        Mockito.when(greetingService.listAllGreetings()).thenReturn(Arrays.asList(new GreetingJSON(text, author)));
        Mockito.when(greetingService.randomGreeting()).thenReturn(new GreetingJSON(text, author));
    }

    @Test
    public void testAllGreetings() {
        JsonPath jsonpath = when().get("/greeting/all").jsonPath();
        String resultingText= jsonpath.getString("text[0]");
        assertEquals(text, resultingText);
    }

    @Test
    public void testRandomGreeting() {

        given()
                .when().get("/greeting")
                .then()
                .statusCode(200)
                .body(containsString("text"));
    }
}
