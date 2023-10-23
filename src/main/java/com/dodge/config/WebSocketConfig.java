package com.dodge.config;

import com.dodge.component.GameWebSocketComponent;
import com.dodge.component.WebSocketComponent;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketComponent webSocketComponent;
    private final GameWebSocketComponent gameWebSocketComponent;

    public WebSocketConfig(WebSocketComponent webSocketComponent, GameWebSocketComponent gameWebSocketComponent) {
        this.webSocketComponent = webSocketComponent;
        this.gameWebSocketComponent = gameWebSocketComponent;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(webSocketComponent, "/home")
                .setAllowedOriginPatterns("*")
                .withSockJS()
                .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js");

        webSocketHandlerRegistry.addHandler(gameWebSocketComponent, "/game")
                .setAllowedOriginPatterns("*")
                .withSockJS()
                .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.5/sockjs.min.js");
    }
}