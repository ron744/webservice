package com.webservice.luxoft.ecxeption;

import java.util.NoSuchElementException;

public class DepartmentNotFoundException extends NoSuchElementException {
    public DepartmentNotFoundException(Long id, NoSuchElementException e) {
        System.out.println("Department with id: " + id + " not found");
        e.printStackTrace();
    }
}
