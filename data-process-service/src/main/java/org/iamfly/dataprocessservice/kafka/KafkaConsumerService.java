package org.iamfly.dataprocessservice.kafka;


import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaConsumerService {


    public Flux<String> consumeMessage() {

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka://9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        ReceiverOptions<Integer, Object> receiverOptions = ReceiverOptions.<Integer, Object>create(props)
                .subscription(Collections.singleton("gateway-dataproc"));

        return KafkaReceiver.create(receiverOptions)
                .receive()
                .map(msg -> {
                    System.out.println("Message: " + msg.toString());
                    return msg.toString();
                });
    }



}
