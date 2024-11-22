package org.iamfly.inputdatagateway.web_socket_settings;

import org.iamfly.inputdatagateway.kafka.KafkaSenderService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
public class WebSocketHandler implements org.springframework.web.reactive.socket.WebSocketHandler {

    private final KafkaSenderService kafkaSenderService;

    public WebSocketHandler(KafkaSenderService kafkaSenderService) {
        this.kafkaSenderService = kafkaSenderService;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .flatMap(kafkaSenderService::sendMetrics).then();
    }

}
