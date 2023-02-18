package io.nayakmk.samples.tracingsample.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class KafkaConsumer {

    @Autowired
    private Tracer tracer;

    @KafkaListener(topics = "tracer-topic")
    public void onKafkaMessage(String message) throws InterruptedException {
        log.info(message);
        Span kafkaSpan = tracer.currentSpan();
        Span newSpan = tracer.nextSpan().name("newSpan").start();
        try (Tracer.SpanInScope ws = tracer.withSpan(newSpan)) {
            TimeUnit.SECONDS.sleep(1);
            newSpan.tag("newSpan tag", "newSpan tag val");
            newSpan.event("New Span Event");
            log.info("I'm in the new span doing some cool work that needs its own span");
        } finally {
            newSpan.end();
        }

        kafkaSpan.tag("newSpan tag", "newSpan tag val");
        TimeUnit.SECONDS.sleep(1);
        log.info("I'm in the original span");
        kafkaSpan.event("I'm in the original span");
    }
}
