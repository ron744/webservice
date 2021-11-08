package com.webservice.luxoft.websetvice.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.webservice.luxoft.websetvice.ecxeption.MyException;
import com.webservice.luxoft.websetvice.model.Employee;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

@Service
public class EmployeeFileWorking {
    private static final Logger log = Logger.getLogger(EmployeeFileWorking.class);

    private static final String EMPLOYEE = "employee";
    private final EmployeeCrud employeeCrud;
    private final static BlockingQueue<Employee> employeeQueue = new ArrayBlockingQueue<>(200);
//    private final Map<UUID, Collection<Exception>>
    private final List<Exception> exceptionList = new ArrayList<>();

    @Autowired
    public EmployeeFileWorking(EmployeeCrud employeeCrud) {
        this.employeeCrud = employeeCrud;

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    employeeCrud.add(employeeQueue.take());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("Exception while taking employees from queue...", e);
                }
            }
        });
    }

    public void readXml(String fileName) throws FileNotFoundException, MyException {
        readStream(new FileInputStream("C:\\test\\" + fileName + ".xml"));
    }

    public void readStream(InputStream employeeXmlStream) throws MyException {
        try {
            XmlMapper xm = new XmlMapper();
            XMLInputFactory xif = XMLInputFactory.newInstance();

            XMLStreamReader xr = xif.createXMLStreamReader(employeeXmlStream);

            while (xr.hasNext()) {
                xr.next();
                if (xr.getEventType() == START_ELEMENT) {
                    if (EMPLOYEE.equals(xr.getLocalName())) {
                        Employee employee = xm.readValue(xr, Employee.class);
                        employeeQueue.put(employee);
                    }
                }
            }
        } catch (XMLStreamException | IOException | InterruptedException e) {
            exceptionList.add(e);
        }

        if (exceptionList.size() > 0)
            throw new MyException(exceptionList);

    }
}
