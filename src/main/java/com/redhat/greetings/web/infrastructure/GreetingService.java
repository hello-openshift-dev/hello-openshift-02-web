package com.redhat.greetings.web.infrastructure;

import com.redhat.greetings.web.domain.GreetingDTO;
import com.redhat.greetings.web.domain.GreetingJSON;
import com.redhat.greetings.web.domain.GreetingSubmission;
import com.redhat.greetings.web.domain.SourceSystem;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.kafka.transactions.KafkaTransactions;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
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

    public Uni<Void> processGreetingSubmission(GreetingJSON greetingJSON) {

        LOGGER.debug("processingGreetingSubmission from: {}", greetingJSON);
        GreetingSubmission greetingSubmission = GreetingSubmission.fromGreetingJSON(greetingJSON);
        greetingDTOEmitter.send(greetingSubmission.toDTO()).thenRun(() -> {
            greetingSubmission.persist();
        });
        return null;
    }


//    @Inject
//    @Channel("greeting-submissions")
//    KafkaTransactions<String> txProducer;
//
//    @Transactional
//    public Uni<Void> processGreetingSubmission(GreetingJSON greetingJSON) {
//
//        return txProducer.withTransaction(emitter ->{
//            LOGGER.debug("processingGreetingSubmission from: {}", greetingJSON);
//            GreetingSubmission greetingSubmission = GreetingSubmission.fromGreetingJSON(greetingJSON);
//            greetingSubmission.persist();
//            emitter.send(Message.of(new GreetingDTO(greetingJSON.text(), greetingJSON.author(), SourceSystem.REST_API)).toString());
//            return Uni.createFrom().voidItem();
//        });
//    }

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

    @Scheduled(every="2m")
    void refreshGreetingJSONList() {

        greetingJSONList.addAll(getGreetings());
    }

    public GreetingJSON randomGreeting() {

        if (greetingJSONList == null || greetingJSONList.isEmpty()) {
            greetingJSONList = getGreetings();
        }
        return greetingJSONList.get(new Random().nextInt(greetingJSONList.size()));
    }

    public List<GreetingSubmission> listAllSubmissions() {

        return GreetingSubmission.listAll();
    }
}
