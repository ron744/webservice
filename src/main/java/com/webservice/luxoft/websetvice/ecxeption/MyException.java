package com.webservice.luxoft.websetvice.ecxeption;

import java.util.List;

public class MyException extends Exception {

    public MyException (List<Exception> exceptionList) {
        System.out.println("This is my exception!");
        for (Exception e : exceptionList) {
            e.printStackTrace();
        }
    }

    public MyException(String msg) {
        super(msg);
    }

    public MyException(Throwable th) {
        super(th);
    }

    public MyException(String msg, Throwable th) {
        super(msg, th);
    }

}
