package com.webservice.luxoft.websetvice.ecxeption;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class LoadingException extends Exception {
    private static final Logger log = Logger.getLogger(LoadingException.class);

    public LoadingException(ConcurrentMap<UUID, List<Exception>> exceptionsMap) {
        System.out.println("This is my exception!");
        for (Map.Entry<UUID, List<Exception>> entry : exceptionsMap.entrySet()) {
            System.out.println("UUID: " + entry.getKey() + ", list exceptions: ");
            for (Exception e : entry.getValue()) {
                e.printStackTrace();
            }
        }
    }

    public LoadingException(String msg) {
        super(msg);
    }

    public LoadingException(Throwable th) {
        super(th);
    }

    public LoadingException(String msg, Throwable th) {
        super(msg, th);
    }

}
