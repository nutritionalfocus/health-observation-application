package uk.co.nfl.web.rest;

import uk.co.nfl.service.HealthObservationApplicationKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health-observation-application-kafka")
public class HealthObservationApplicationKafkaResource {

    private final Logger log = LoggerFactory.getLogger(HealthObservationApplicationKafkaResource.class);

    private HealthObservationApplicationKafkaProducer kafkaProducer;

    public HealthObservationApplicationKafkaResource(HealthObservationApplicationKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.send(message);
    }
}
