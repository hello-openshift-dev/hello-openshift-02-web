package com.redhat.greetings.web.domain;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class GreetingDTOTest {

    String text = "Hi, there!";

    String author = "Iggy";

    Instant created = Instant.now();

    @Test
    public void testConstructors() {

        GreetingDTO greetingDTO = new GreetingDTO(text, author, SourceSystem.REST_API);
        assertFalse(greetingDTO.isFamilyFriendly());
        assertNull(greetingDTO.createdAt());
        assertEquals(text, greetingDTO.text());
        assertEquals(author, greetingDTO.author());
    }

    @Test
    public void testOtherConstructor() {

        GreetingDTO greetingDTO = new GreetingDTO(text, author, SourceSystem.REST_API, true);
        assertTrue(greetingDTO.isFamilyFriendly());
        assertNull(greetingDTO.createdAt());
        assertEquals(text, greetingDTO.text());
        assertEquals(author, greetingDTO.author());
    }
}
