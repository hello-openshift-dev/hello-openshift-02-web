package com.redhat.greetings.web.api;

import com.redhat.greetings.web.domain.GreetingSubmission;
import com.redhat.greetings.web.domain.SourceSystem;
import com.redhat.greetings.web.infrastructure.GreetingService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class RESTApiListAllGreetingsTest {

    static final Logger LOGGER = LoggerFactory.getLogger(RESTApiListAllGreetingsTest.class);

    @InjectMock
    GreetingService greetingService;

    String text = "Hi, there JaCoCo!";
    String author = "Lemmy Kilminster";


    @BeforeEach
    void setUp() {
//        Mockito.when(greetingService.listAllSubmissions()).thenReturn(Arrays.asList(new GreetingSubmission(text, author, SourceSystem.REST_API, Instant.now())));
    }

    @Test
    public void testAllGreetings() {

        JsonPath jsonpath = when().get("/greetings").jsonPath();
        String resultingText= jsonpath.getString("text[0]");
        assertEquals(text, resultingText);
    }

}
