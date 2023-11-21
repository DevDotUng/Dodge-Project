package com.dodge.component;

import com.dodge.dto.MessageDataDTO;
import com.dodge.service.DodgeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Component
public class GameWebSocketComponent extends TextWebSocketHandler {

    private final WebSocketComponent webSocketComponent;
    public GameWebSocketComponent(WebSocketComponent webSocketComponent) {
        this.webSocketComponent = webSocketComponent;
    }

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    DodgeService dodgeService;

    public static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessionMap.put(session.getId(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws JsonProcessingException {
        String socketMessage = message.getPayload().toString();
        MessageDataDTO messageData = new MessageDataDTO("a", "a");
        System.out.println(socketMessage.charAt(9));
        if (socketMessage.charAt(9) != 'p') {
            messageData = mapper.readValue(socketMessage, MessageDataDTO.class);
        }
        if (messageData.getType().equals("update")) {
            dodgeService.updateHostId(messageData.getTitle(), session.getId());
        } else if (messageData.getType().equals("enter")) {
            dodgeService.updateGuestId(messageData.getTitle(), session.getId());
        } else if (messageData.getType().equals("finish")) {
            sessionMap.forEach((sessionId, sessionInMap) -> {
                try {
                    String guestId = dodgeService.getGuestId(session.getId());
                    if (guestId.equals(sessionId)) {
                        sessionInMap.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else if (socketMessage.charAt(9) == 'p') {
            sessionMap.forEach((sessionId, sessionInMap) -> {
                try {
                    String guestId = dodgeService.getGuestId(session.getId());
                    if (guestId.equals(sessionId)) {
                        sessionInMap.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

//        sessionMap.forEach((sessionId, sessionInMap) -> {
//            try {
//                sessionInMap.sendMessage(new TextMessage(messageDataString));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws JsonProcessingException {
        String closedSessionId = session.getId();
        sessionMap.remove(closedSessionId);
        String title = dodgeService.deleteRoom(closedSessionId);
        MessageDataDTO messageData = new MessageDataDTO("delete", title);
        String messageDataString = mapper.writeValueAsString(messageData);

        Map<String, WebSocketSession> homeSessionMap = webSocketComponent.getSessionMap();
        homeSessionMap.forEach((sessionId, sessionInMap) -> {
            try {
                sessionInMap.sendMessage(new TextMessage(messageDataString));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}