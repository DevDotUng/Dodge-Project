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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Component
public class WebSocketComponent extends TextWebSocketHandler {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    DodgeService dodgeService;

    public Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessionMap.put(session.getId(), session);
        dodgeService.addUser(session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws JsonProcessingException {
        String title = message.getPayload().toString();
        dodgeService.createRoom(title, session.getId());
        MessageDataDTO messageData = new MessageDataDTO("insert", title);
        String messageDataString = mapper.writeValueAsString(messageData);
        sessionMap.forEach((sessionId, sessionInMap) -> {
            try {
                sessionInMap.sendMessage(new TextMessage(messageDataString));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws JsonProcessingException {
//        String closedSessionId = session.getId();
        dodgeService.deleteUser(session.getId());
        sessionMap.remove(session.getId());
//        String title = dodgeService.deleteRoom(closedSessionId);
//        MessageDataDTO messageData = new MessageDataDTO("delete", title);
//        String messageDataString = mapper.writeValueAsString(messageData);
//        sessionMap.forEach((sessionId, sessionInMap) -> {
//            try {
//                sessionInMap.sendMessage(new TextMessage(messageDataString));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
    }

    public Map<String, WebSocketSession> getSessionMap() {
        return this.sessionMap;
    }
}