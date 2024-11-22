package org.iamfly.inputdatagateway.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;

@Service
public class KafkaSenderService {

    private final KafkaSender<Integer, Object> kafkaSender;

    public KafkaSenderService(SenderOptions<Integer, Object> senderOptions) {
        this.kafkaSender = KafkaSender.create(senderOptions);
    }

    public Flux<Void> sendMetrics(Object metrics) {
        return kafkaSender.send(Mono.just(SenderRecord.create(new ProducerRecord<>("gateway-dataproc", null, System.currentTimeMillis(), 1, metrics), null)))
                .onErrorResume(Mono::error)
                .then().flux();

    }


}
