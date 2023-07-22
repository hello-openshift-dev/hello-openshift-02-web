package com.redhat.greetings.web.infrastructure;

import com.redhat.greetings.web.domain.GreetingDTO;
import com.redhat.greetings.web.domain.SourceSystem;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;

@ApplicationScoped
public class CQRSService {

    static final Logger LOGGER = LoggerFactory.getLogger(CQRSService.class);
    public Collection<GreetingDTO> listAllGreetings() {
        return Arrays.asList(new GreetingDTO("Hi, there!", "Angus Young", SourceSystem.REST_API), new GreetingDTO("Rock on!", "Malcolm Young", SourceSystem.REST_API));
    }

}
