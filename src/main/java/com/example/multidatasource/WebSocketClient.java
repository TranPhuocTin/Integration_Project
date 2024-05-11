package com.example.multidatasource;

import com.example.multidatasource.entity.merge.MergePerson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WebSocketClient {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSession session = stompClient.connect("ws://localhost:8080/ws", new StompSessionHandlerAdapter() {}).get();

        System.out.println("WebSocket connection is open: " + session.isConnected());

        session.subscribe("/topic/merge", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return new ParameterizedTypeReference<List<MergePerson>>() {}.getType();
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    List<LinkedHashMap<String, Object>> dataList = (List<LinkedHashMap<String, Object>>) payload;
                    List<MergePerson> mergePersonList = new ArrayList<>();
                    for (LinkedHashMap<String, Object> data : dataList) {
                        MergePerson mergePerson = mapper.convertValue(data, MergePerson.class);
                        mergePersonList.add(mergePerson);
                    }
                    mergePersonList.forEach(mergePerson -> System.out.println("Received message: " + mergePerson));
                } catch (Exception e) {
                    System.out.println("Error handling message: " + e.getMessage());
                }
            }
        });

        Thread.sleep(Long.MAX_VALUE);
    }
}