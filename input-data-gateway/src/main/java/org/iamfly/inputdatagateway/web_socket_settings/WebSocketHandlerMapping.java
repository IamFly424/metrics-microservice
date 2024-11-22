package org.iamfly.inputdatagateway.web_socket_settings;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketHandlerMapping {

    @Bean
    public HandlerMapping handlerMapping(WebSocketHandler webSocketHandler) {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/ws", webSocketHandler);

        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setUrlMap(map);
        handlerMapping.setOrder(-1);
        return handlerMapping;
    }
}
