package com.redhat.greetings.web.domain;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.Instant;

@Entity
public class GreetingSubmission extends PanacheEntity {

    String text;

    String author;

    SourceSystem sourceSystem = SourceSystem.REST_API;

    Instant submittedAt;

    public static GreetingSubmission fromGreetingJSON(GreetingJSON greetingJSON) {
        return new GreetingSubmission(
                greetingJSON.text(),
                greetingJSON.author(),
                SourceSystem.REST_API,
                Instant.now());
    }
    public GreetingSubmission() {
    }

    public GreetingSubmission(String text, String author, SourceSystem sourceSystem, Instant submittedAt) {
        this.text = text;
        this.author = author;
        this.sourceSystem = sourceSystem;
        this.submittedAt = submittedAt;
    }

    public GreetingDTO toDTO() {
        return new GreetingDTO(this.text, this.author, this.sourceSystem);
    }

    public Long getId() {
        return id;
    }
    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public SourceSystem getSourceSystem() {
        return sourceSystem;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }
}
