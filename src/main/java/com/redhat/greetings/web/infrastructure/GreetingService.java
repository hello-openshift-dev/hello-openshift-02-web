package com.redhat.greetings.web.infrastructure;

import com.redhat.greetings.web.domain.GreetingDTO;
import com.redhat.greetings.web.domain.GreetingJSON;
import com.redhat.greetings.web.domain.GreetingSubmission;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@ApplicationScoped
public class GreetingService {

    static final Logger LOGGER = LoggerFactory.getLogger(GreetingService.class);
    
    List<GreetingJSON> greetingJSONList;

    @Inject
    CQRSService CQRSService;

    @Channel("greeting-submissions")
    Emitter<GreetingDTO> greetingDTOEmitter;

   @WithTransaction
   public Uni<Void> processGreetingSubmission(GreetingJSON greetingJSON) {

    LOGGER.debug("processingGreetingSubmission from: {}", greetingJSON);
        GreetingSubmission greetingSubmission = GreetingSubmission.fromGreetingJSON(greetingJSON);
        return greetingSubmission.persistAndFlush().onItem().invoke(() -> {
            greetingDTOEmitter.send(greetingSubmission.toDTO());
        }).replaceWithVoid();
    }

    public List<GreetingJSON> listAllGreetings() {

        if (greetingJSONList == null || greetingJSONList.isEmpty()) {
            greetingJSONList = getGreetings();
        }
        return greetingJSONList;
    }

    private List<GreetingJSON> getGreetings() {

        return CQRSService.listAllGreetings().stream().map(greetingDTO -> {
            return new GreetingJSON(greetingDTO.text(), greetingDTO.author());
        }).collect(Collectors.toList());
    }

    @PostConstruct
    void setUp() {

//        greetingJSONList = getGreetings();
//        LOGGER.debug("greetingJSONList hydrated");
    }

//    @Scheduled(every="2m")
//    void refreshGreetingJSONList() {
//
//        greetingJSONList.addAll(getGreetings());
//    }

    public GreetingJSON randomGreeting() {

        if (greetingJSONList == null || greetingJSONList.isEmpty()) {
            greetingJSONList = getGreetings();
        }
        return greetingJSONList.get(new Random().nextInt(greetingJSONList.size()));
    }

    @WithTransaction
    public Uni<List<GreetingSubmission>> listAllSubmissions() {

        return GreetingSubmission.listAll();
    }
}
