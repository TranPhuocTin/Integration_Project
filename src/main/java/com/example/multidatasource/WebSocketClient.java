package com.example.multidatasource;

import com.example.multidatasource.entity.merge.MergePerson;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

public class WebSocketClient {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession session = stompClient.connect("ws://localhost:8080/ws", new StompSessionHandlerAdapter() {}).get();

        session.subscribe("/topic", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return MergePerson.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                MergePerson mergePerson = (MergePerson) payload;
                System.out.println("Received message: " + mergePerson);
            }
        });

        // Keep the connection open until the application exits
        Thread.sleep(Long.MAX_VALUE);
    }
}