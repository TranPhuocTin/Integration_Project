package com.example.multidatasource.service.imp;

import com.example.multidatasource.entity.merge.MergePerson;
import com.example.multidatasource.service.SocketService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SocketServiceImp implements SocketService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public SocketServiceImp(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void sendMessage(String topic, Object payload) {
        simpMessagingTemplate.convertAndSend(topic, payload);
    }
}
