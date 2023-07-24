package com.redhat.greetings.web.infrastructure;

import io.quarkus.test.junit.QuarkusTestProfile;

/**
 * Empty profile used to start a new instance of Quarkus so that the @PostConstruct method in GreetingService is injected
 * with the correct Mock in GreetingServiceTest
 */

public class WebInfrastructureGreetingServiceTestProfile implements QuarkusTestProfile {
}
