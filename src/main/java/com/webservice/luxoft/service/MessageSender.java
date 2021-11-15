package com.webservice.luxoft.service;

public interface MessageSender<T> {
    void send(T message);
}
