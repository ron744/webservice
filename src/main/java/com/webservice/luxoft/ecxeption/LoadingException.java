package com.webservice.luxoft.ecxeption;

import java.util.List;

public class LoadingException extends Exception {
    public LoadingException(List<Exception> exceptions) {
        System.out.println("This is my exception!");
        for (Exception e : exceptions) {
            e.printStackTrace();
        }
    }
}
