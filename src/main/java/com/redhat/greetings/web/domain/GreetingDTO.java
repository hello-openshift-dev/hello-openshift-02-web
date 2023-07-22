package com.redhat.greetings.web.domain;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.io.Serializable;
import java.time.Instant;

public record GreetingDTO(Long id, String text, String author, SourceSystem sourceSystem, Instant createdAt, boolean isFamilyFriendly) {
    public GreetingDTO(String text, String author, SourceSystem sourceSystem) {
        this(null, text, author, sourceSystem, null, false);
    }

    public GreetingDTO(String text, String author, SourceSystem sourceSystem, boolean isVerifiedFamilyFriendly) {
        this(null, text, author, sourceSystem, null, isVerifiedFamilyFriendly);
    }
}
