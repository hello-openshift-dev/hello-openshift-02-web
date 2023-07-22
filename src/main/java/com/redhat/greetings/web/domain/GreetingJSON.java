package com.redhat.greetings.web.domain;

import java.io.Serializable;

public record GreetingJSON(String text, String author) implements Serializable {
}
