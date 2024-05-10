package com.example.multidatasource.service;

import com.example.multidatasource.entity.merge.MergePerson;

public interface SocketService {
    void sendMessage(String topic, MergePerson payload);
}
